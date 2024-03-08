package com.example.macrocounter.model.entity

import com.squareup.moshi.JsonClass



data class Spec(
    val attribute: String,
    val optioned: String
){
    override fun toString(): String = "$attribute: $optioned"
}


@JsonClass(generateAdapter = true)
data class CartItemEntity(
    val food: FoodEntity,
    val spec: List<Spec>?
)


@JsonClass(generateAdapter = true)
data class CartItemForViewEntity(
    val food: FoodEntity,
    val spec: List<Spec>?,
    var quantity: Int,
)