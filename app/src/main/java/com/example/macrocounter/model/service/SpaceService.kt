package com.example.macrocounter.model.service

import com.example.macrocounter.model.Network
import com.example.macrocounter.model.entity.SpaceListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SpaceService {

    @GET("spaces")
    suspend fun list(
        @Header("Authorization")token: String,
        @Query("index") index: Int,
        @Query("limit") limit: Int
    ): Response<SpaceListResponse>


    companion object {
        fun instance(): SpaceService {
            return Network.createService(SpaceService::class.java)
        }
    }
}