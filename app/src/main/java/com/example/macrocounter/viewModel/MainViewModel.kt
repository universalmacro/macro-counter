package com.example.macrocounter.viewModel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.SmartDisplay
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.macrocounter.model.entity.Category
import com.example.macrocounter.model.service.CategoryService
import com.example.macrocounter.model.service.FoodService

class MainViewModel : ViewModel() {

    private val categoryService = CategoryService.instance()

    //分类数据是否加载成功
    var categoryLoaded by mutableStateOf(false)
        private set

    //分类数据
    var categories by mutableStateOf(
        listOf(
            Category("飲品", "1"),
            Category("料理", "2"),
            Category("其他", "3"),
        )
    )
        private set

//    suspend fun categoryData() {
//        val categoryRes = categoryService.category()
//        if (categoryRes.code == 0) {
//            categories = categoryRes.data
//            categoryLoaded = true
//        } else {
//            //不成功的情况下，读取 message
//            val message = categoryRes.message
//        }
//    }

    //当前分类下标
    var categoryIndex by mutableStateOf(0)
        private set

    /**
     * 更新分类下标
     *
     * @param index
     */
    fun updateCategoryIndex(index: Int) {
        categoryIndex = index
    }


    //当前类型下标
    var currentTypeIndex by mutableStateOf(0)
        private set

    //是否文章列表
    var showArticleList by mutableStateOf(true)
        private set

    /**
     * 更新类型下标
     *
     * @param index
     */
    fun updateTypeIndex(index: Int) {
        currentTypeIndex = index
        showArticleList = currentTypeIndex == 0
    }




}