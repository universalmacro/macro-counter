package com.example.macrocounter.model

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object Network {


    //文档地址：https://docs.apipost.cn/preview/1a28e17fa3c8f473/16838456ae6dc5c7
    //mock 数据请求 url
//    private const val baseUrl =
//        "https://mock.apipost.cn/app/mock/project/ced69cf2-9206-4a42-895e-dd7442a888df/"

    private const val baseUrl = "https://uat.uat-hongkong-1.universalmacro.com/"

    var logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    val httpClient = OkHttpClient.Builder().addInterceptor(logging)



    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(httpClient.build())
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
            )
        ).build()

    fun <T> createService(clazz: Class<T>): T {
        return retrofit.create(clazz)
    }
}


