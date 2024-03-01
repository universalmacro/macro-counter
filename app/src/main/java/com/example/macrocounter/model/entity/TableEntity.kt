package com.example.macrocounter.model.entity


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TableEntity(
    val label: String,
    var id: String,
)

data class TableListResponse(val items: List<TableEntity>?) : BaseResponse()

//data class SpaceInfoResponse(val data: SpaceEntity?) : BaseResponse()