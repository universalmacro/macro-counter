package com.example.macrocounter.viewModel

import android.content.Context
import com.example.macrocounter.model.entity.TableEntity
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.macrocounter.model.service.TableService
import kotlinx.coroutines.delay

class TableViewModel(context: Context) : ViewModel() {

    private val tableService = TableService.instance()


    var list by mutableStateOf(emptyArray<TableEntity>())
        private set

    //第一页文章列表数据是否加载完成
    var listLoaded by mutableStateOf(false)
        private set

    //是否正在刷新
    var selectedTable by mutableStateOf("")

    //是否还有更多
    private var hasMore = false

    suspend fun fetchTableList(token: String, spaceId: String) {
        val res = tableService.list(token = "Bearer $token", spaceId = spaceId)
        if (res != null) {
            val tmpList = mutableListOf<TableEntity>()
            tmpList.addAll(res)

            //是否还有更多数据
            list = tmpList.toTypedArray()
            listLoaded = true
        }
    }



    private var tableEntity: TableEntity? = null


}