package com.example.macrocounter.model.service

import com.example.macrocounter.model.Network
import com.example.macrocounter.model.entity.SpaceListResponse
import com.example.macrocounter.model.entity.TableEntity
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface TableService {

    @GET("spaces/{spaceId}/tables")
    suspend fun list(
        @Header("Authorization")token: String,
        @Path("spaceId") spaceId: String,
    ): Array<TableEntity>


    companion object {
        fun instance(): TableService {
            return Network.createService(TableService::class.java)
        }
    }
}