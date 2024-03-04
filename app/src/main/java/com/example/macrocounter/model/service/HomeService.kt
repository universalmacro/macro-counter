package com.example.macrocounter.model.service

import com.example.macrocounter.model.Network
import com.example.macrocounter.model.entity.CategoryResponse
import retrofit2.http.GET

interface HomeService {

    @GET("category/list")
    suspend fun category(): CategoryResponse


    companion object {
        fun instance(): HomeService {
            return Network.createService(HomeService::class.java)
        }
    }


}