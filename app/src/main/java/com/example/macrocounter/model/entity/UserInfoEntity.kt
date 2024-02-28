package com.example.macrocounter.model.entity

data class UserInfoEntity(val userName: String)

data class UserInfoResponse(val data: UserInfoEntity?) : BaseResponse()