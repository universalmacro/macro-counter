package com.example.macrocounter.model.service


import android.util.Log
import com.example.macrocounter.model.Network
import com.example.macrocounter.model.entity.CategoryResponse
import com.example.macrocounter.model.entity.SpaceListResponse
import com.example.macrocounter.model.entity.UserInfoResponse
import retrofit2.http.*

interface UserService {
//    @FormUrlEncoded
//    @Headers("Content-type: application/json")
//    @POST("sessions")
//    suspend fun signIn(
//        @Field("account") account: String,
//        @Field("password") password: String,
//    ): UserInfoResponse

    data class AuthData(
        val account: String,
        val password: String
    )

    @Headers("Content-type: application/json")
    @POST("sessions")
    suspend fun signIn(
        @Body body: AuthData,
    ): UserInfoResponse


    companion object {
        fun instance(): UserService {
            return Network.createService(UserService::class.java)
        }
    }

}
