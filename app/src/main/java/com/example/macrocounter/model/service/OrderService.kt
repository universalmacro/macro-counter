package com.example.macrocounter.model.service

import com.example.macrocounter.model.Network
import com.example.macrocounter.model.entity.CartItemEntity
import com.example.macrocounter.model.entity.FoodEntity
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path



interface OrderService {

    data class CreateOrderRequest(
        val tableLabel: String? = "",
        val remark: String? = "",
        val foods:  List<CartItemEntity>
    )


    @POST("spaces/{spaceId}/order")
    suspend fun createOrder(
        @Header("Authorization")token: String,
        @Path("spaceId") spaceId: String,
        @Body createOrderRequest: CreateOrderRequest
    ): CreateOrderRequest


    companion object {
        fun instance(): OrderService {
            return Network.createService(OrderService::class.java)
        }
    }
}