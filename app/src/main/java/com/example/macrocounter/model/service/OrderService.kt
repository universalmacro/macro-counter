package com.example.macrocounter.model.service

import androidx.compose.runtime.MutableState
import com.example.macrocounter.model.Network
import com.example.macrocounter.model.entity.CartItemEntity
import com.example.macrocounter.model.entity.OrderEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query


interface OrderService {

    data class CreateOrderRequest(
        val tableLabel: String? = "",
        val remark: String? = "",
        val foods:  List<CartItemEntity>
    )

    data class CreateBillRequest(
        val amount: Int,
        val orderIds:  List<String>
    )

    data class OrderRequest(
        val foods: List<CartItemEntity?>
    )

    data class UpdateLabelRequest(
        val tableLabel:  String
    )


    @POST("spaces/{spaceId}/orders")
    suspend fun createOrder(
        @Header("Authorization")token: String,
        @Path("spaceId") spaceId: String,
        @Body createOrderRequest: CreateOrderRequest
    ): CreateOrderRequest

    @PATCH("orders/{orderId}/addition")
    suspend fun addOrder(
        @Header("Authorization")token: String,
        @Path("orderId") orderId: String,
        @Body addOrderRequest: OrderRequest
    )

    @PATCH("orders/{orderId}/cancel")
    suspend fun cancelOrder(
        @Header("Authorization")token: String,
        @Path("orderId") orderId: String,
        @Body cancelOrderRequest: OrderRequest
    ): Response<Unit>


    @GET("spaces/{spaceId}/orders")
    suspend fun listOrder(
        @Header("Authorization")token: String,
        @Path("spaceId") spaceId: String,
        @Query("statuses") statuses: List<String>? = null,
        @Query("tableLabels") tableLabels: List<String>? = null,
    ): Response<List<OrderEntity>>


    @POST("orders/bills")
    suspend fun createBill(
        @Header("Authorization")token: String,
        @Body createBillRequest: CreateBillRequest
    ): CreateBillRequest

    @POST("orders/bills/print")
    suspend fun printBill(
        @Header("Authorization")token: String,
        @Body printBillRequest: CreateBillRequest
    ): CreateBillRequest


    @PUT("orders/{orderId}/tableLabel")
    suspend fun switchTable(
        @Header("Authorization")token: String,
        @Path("orderId") orderId: String,
        @Body updateLabelRequest: UpdateLabelRequest
    )

    companion object {
        fun instance(): OrderService {
            return Network.createService(OrderService::class.java)
        }
    }
}