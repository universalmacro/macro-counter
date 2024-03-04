package com.example.macrocounter.model.entity

data class UserInfoEntity(val token: String)

//data class UserInfoResponse(val data: UserInfoEntity?) : BaseResponse()

data class UserInfoResponse(val token: String?) : BaseResponse()
