package com.example.macrocounter.viewModel

import android.content.Context
import com.example.macrocounter.model.entity.TableEntity
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.macrocounter.model.entity.FoodEntity
import com.example.macrocounter.model.service.CategoryService
import com.example.macrocounter.model.service.FoodService
import kotlinx.coroutines.delay
import org.json.JSONObject

class FoodViewModel(context: Context) : ViewModel() {

    private val foodService = FoodService.instance()
    private val categoryService = CategoryService.instance()


    var list by mutableStateOf(emptyArray<FoodEntity>())
        private set

    var categories by mutableStateOf(emptyArray<String>())
        private set


    var listWithCategory by mutableStateOf(mutableMapOf<String, List<FoodEntity>>())
        private set

    //第一页文章列表数据是否加载完成
    var listLoaded by mutableStateOf(false)
        private set

    var selectedTable: TableEntity? by mutableStateOf(null)

    //是否还有更多
    private var hasMore = false

    var error by mutableStateOf("")
        private set


    suspend fun fetchFoodList(token: String, spaceId: String) {
        val category = categoryService.list(token = "Bearer $token", spaceId = spaceId)
        val res = foodService.list(token = "Bearer $token", spaceId = spaceId)
        if (res.isSuccessful) {
            if (res != null && category != null) {
                val tmpList = mutableListOf<FoodEntity>()
                res.body()?.let { tmpList.addAll(it) }

                list = tmpList.toTypedArray()
                if(category.body() == null){
                    categories = emptyList<String>().toTypedArray()
                }else{
                    categories = category.body()?.toTypedArray()!!

                    for (value in category.body()!!) {
                        listWithCategory[value] = list.filter{ it.categories!![0] == value}
                    }

                }
                listLoaded = true
            }  else {
                //失败
                val jsonObj = JSONObject(res.errorBody()!!.charStream().readText())
                Log.d("Erreo->Result", " ${jsonObj} " )
                error = jsonObj.toString()
            }
        }

    }



    private var foodEntity: FoodEntity? = null


}