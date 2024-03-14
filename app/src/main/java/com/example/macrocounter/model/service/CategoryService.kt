package com.example.macrocounter.model.service

import com.example.macrocounter.model.Network
import com.example.macrocounter.model.entity.CategoryResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface CategoryService {

    @GET("spaces/{spaceId}/foods/categories")
    suspend fun list(
        @Header("Authorization")token: String,
        @Path("spaceId") spaceId: String,
    ): List<String>?


    companion object {
        fun instance(): CategoryService {
            return Network.createService(CategoryService::class.java)
        }
    }


}