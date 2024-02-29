package com.example.macrocounter.model.entity

/**
 * 分类
 *
 * @property label
 */
data class Category(
    val label: String,
    val id: String
)

/**
 * Category Response
 *
 * @property data
 */
data class CategoryResponse(var data: List<Category>) : BaseResponse() {}

