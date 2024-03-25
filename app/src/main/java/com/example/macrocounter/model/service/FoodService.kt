package com.example.macrocounter.model.service

import com.example.macrocounter.model.Network
import com.example.macrocounter.model.entity.FoodEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface FoodService {

    @GET("spaces/{spaceId}/foods")
    suspend fun list(
        @Header("Authorization")token: String,
        @Path("spaceId") spaceId: String,
    ): Response<List<FoodEntity>?>


    companion object {
        fun instance(): FoodService {
            return Network.createService(FoodService::class.java)
        }
    }
}