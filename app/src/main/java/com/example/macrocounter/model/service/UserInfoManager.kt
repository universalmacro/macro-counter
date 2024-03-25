package com.example.macrocounter.model.service

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.net.URL

class UserInfoManager(private val context: Context) {

    companion object {
        private val Context.userStore: DataStore<Preferences> by preferencesDataStore("user_store")

        val LOGGED = booleanPreferencesKey("LOGGED")
        val TOKEN = stringPreferencesKey("TOKEN")
        val URL = stringPreferencesKey("URL")
        val ACCOUNT = stringPreferencesKey("ACCOUNT")
        val PASSWORD = stringPreferencesKey("PASSWORD")

        private var instance: UserInfoManager? = null
        fun getInstance(context: Context): UserInfoManager {
            return instance ?: synchronized(this) {
                instance ?: UserInfoManager(context).also { instance = it }
            }
        }

    }

    val logged: Flow<Boolean> = context.userStore.data.map { it[LOGGED] ?: false }
    val token: Flow<String> = context.userStore.data.map { it[TOKEN] ?: "" }
    val url: Flow<String> = context.userStore.data.map { it[URL] ?: "https://uat.uat-hongkong-1.universalmacro.com/" }
    val account: Flow<String> = context.userStore.data.map { it[ACCOUNT] ?: "" }
    val password: Flow<String> = context.userStore.data.map { it[PASSWORD] ?: "" }

    suspend fun getBaseUrl() : String? {
        val preference = context.userStore.data.first()
        return preference[URL]
    }


    /**
     * 存储用户信息
     *
     * @param userToken
     */
    suspend fun save(token: String) {
        context.userStore.edit {
            it[LOGGED] = token.isNotEmpty()
            it[TOKEN] = token
        }
    }

    suspend fun saveUrl(url: String) {
        context.userStore.edit {
            it[URL] = url
        }
    }

    suspend fun savePassword(account: String, password: String) {
        context.userStore.edit {
            it[ACCOUNT] = account
            it[PASSWORD] = password
        }
    }

    /**
     * 清空用户登录数据
     *
     */
    suspend fun clearLogin() {
        context.userStore.edit {
            it[LOGGED] = false
            it[TOKEN] = ""
        }
    }

    suspend fun clearPassword() {
        context.userStore.edit {
            it[ACCOUNT] = ""
            it[PASSWORD] = ""
        }
    }

}