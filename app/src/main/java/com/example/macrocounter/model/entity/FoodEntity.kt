package com.example.macrocounter.model.entity


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

enum class Status {
    AVAILABLE, UNAVAILABLE
}

data class Option(
    val label: String,
    val extra: Int,
)

data class Attribute(
    val label: String,
    val options: List<Option>,
)

@JsonClass(generateAdapter = true)
data class FoodEntity(
    var id: String,
    val name: String,
    val description: String,
    val status: String,
    val image: String,
    val price: Int,
    val fixedOffset: Int?,
    val categories: List<String>,
    val attributes: List<Attribute>,
)

data class FoodListResponse(val items: List<FoodEntity>?) : BaseResponse()

data class FoodInfoResponse(val data: FoodEntity?) : BaseResponse()