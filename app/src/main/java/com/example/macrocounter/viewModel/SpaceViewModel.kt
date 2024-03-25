package com.example.macrocounter.viewModel


import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.macrocounter.model.entity.SpaceEntity
import com.example.macrocounter.model.service.SpaceService
import kotlinx.coroutines.delay

class SpaceViewModel(context: Context) : ViewModel() {

    private val spaceService = SpaceService.instance()

    private val pageSize = 0
    private var pageOffset = 1

    //文章列表数据
    var list by mutableStateOf(emptyArray<SpaceEntity>())
        private set

    //第一页文章列表数据是否加载完成
    var listLoaded by mutableStateOf(false)
        private set

    //是否正在刷新
    var refreshing by mutableStateOf(false)
        private set

    //是否还有更多
    private var hasMore = false

    var selectedSpace: SpaceEntity? by mutableStateOf(null)


    var error by mutableStateOf("")
        private set



    suspend fun fetchSpaceList(token: String) {
        try{
            val res = spaceService.list(index = pageOffset, limit = pageSize, token = "Bearer $token")

            if(res.isSuccessful){
                if (res.body()?.items != null) {
                    val tmpList = mutableListOf<SpaceEntity>()
                    if (pageOffset != 1) {
                        tmpList.addAll(list)
                    }
                    tmpList.addAll(res.body()?.items!!)
                    //是否还有更多数据
                    hasMore = res.body()?.items!!.size == pageSize
                    list = tmpList.toTypedArray()
                    listLoaded = true
                    refreshing = false
                } else {
                    pageOffset--
                    if (pageOffset <= 1) {
                        pageOffset = 1
                    }
                }
            }else{
                Log.d("=====Exception=====", "nooooooooooooooo ")

            }
        }catch (exception: Exception) {
            // handle errors
            Log.d("=====Exception=====", "${exception} ")
//            error = "網絡錯誤"
            error = exception.message.toString()
        }



    }

    /**
     * 下拉刷新
     *
     */
    suspend fun refresh(token: String) {
        pageOffset = 1
//        listLoaded = false
        refreshing = true
        fetchSpaceList(token = token)
    }

    suspend fun loadMore(token: String) {
        if (hasMore) {
            pageOffset++
            fetchSpaceList(token = token)
        }
    }

    //HTML 头部
    private val htmlHeader = """
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title></title>
            <style>
                img {
                    max-width: 100% !important;
                }
            </style>
        </head>
        <body>
    """.trimIndent()

    //html尾部
    private val htmlFooter = """
        </body>
        </html>
    """.trimIndent()


    private var spaceEntity: SpaceEntity? = null

    var content by mutableStateOf(
        """$htmlHeader
        ${spaceEntity?.description ?: ""}
        $htmlFooter
    """.trimIndent()
    )

    var infoLoaded by mutableStateOf(false)
        private set

//    suspend fun fetchInfo() {
//        val res = articleService.info("")
//        if (res.code == 0 && res.data != null) {
//            spaceEntity = res.data
//            content = """$htmlHeader
//                            ${spaceEntity?.description ?: ""}
//                            $htmlFooter
//                        """.trimIndent()
//            infoLoaded = true
//        }
//    }
}