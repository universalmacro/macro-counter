package com.example.macrocounter.model.service


import com.example.macrocounter.model.Network
import com.example.macrocounter.model.entity.UserInfoResponse
import retrofit2.http.*

interface UserService {
    @FormUrlEncoded
    @POST("sessions")
    suspend fun signIn(
        @Field("userName") useName: String,
        @Field("password") password: String,
    ): UserInfoResponse

    companion object {
        fun instance(): UserService {
            return Network.createService(UserService::class.java)
        }
    }

}