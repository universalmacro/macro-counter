package com.example.macrocounter.model.entity



import androidx.compose.runtime.snapshots.SnapshotStateList
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrderEntity(
    var id: String,
    var code: String? = "",
    val tableLabel: String,
//    val foods: SnapshotStateList<CartItemEntity?>,
    val foods: MutableList<CartItemEntity?>,
    val status: String,
    val createdAt: String,
    val updatedAt: String,

    )

data class OrderListResponse(val items: List<OrderEntity>?) : BaseResponse()

