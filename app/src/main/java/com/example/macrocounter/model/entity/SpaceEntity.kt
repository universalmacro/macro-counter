package com.example.macrocounter.model.entity


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SpaceEntity(
    val name: String,
    var id: String,
    var description: String? = ""
)

data class SpaceListResponse(val data: List<SpaceEntity>?) : BaseResponse()

data class SpaceInfoResponse(val data: SpaceEntity?) : BaseResponse()