package com.example.macrocounter.model.service

import com.example.macrocounter.model.Network
//import com.example.macrocounter.model.entity.ArticleInfoResponse
import com.example.macrocounter.model.entity.SpaceListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SpaceService {

    @GET("article/list")
    suspend fun list(
        @Query("pageOffset") pageOffset: Int,
        @Query("pageSize") pageSize: Int
    ): SpaceListResponse

//    @GET("article/info")
//    suspend fun info(@Query("id") id: String): ArticleInfoResponse

    companion object {
        fun instance(): SpaceService {
            return Network.createService(SpaceService::class.java)
        }
    }
}