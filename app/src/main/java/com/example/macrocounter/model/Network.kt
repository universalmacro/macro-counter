package com.example.macrocounter.model

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import com.example.macrocounter.compositionLocal.LocalUserViewModel
import com.example.macrocounter.model.service.UserInfoManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import java.util.prefs.Preferences

object Network {

    private const val baseUrl = "https://uat.uat-hongkong-1.universalmacro.com/"

    //    var baseUrl = UserInfoManager.URL
//    val userViewModel = LocalUserViewModel.current


    var logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    val httpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(logging)//.addInterceptor(TokenInterceptor())


    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
//        .baseUrl(UserInfoManager.getInstance(this))
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




