package com.example.macrocounter.viewModel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.macrocounter.model.entity.UserInfoEntity
import com.example.macrocounter.model.service.UserInfoManager
import com.example.macrocounter.model.service.UserService
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.security.MessageDigest


class UserViewModel(context: Context) : ViewModel() {

    private val userInfoManager = UserInfoManager(context)
    private val userService = UserService.instance()

    var account by mutableStateOf("")
    var password by mutableStateOf("")
    var baseUrl by mutableStateOf("")

    var userInfo: UserInfoEntity? = null
        private set

    init {
        //其实这里可以使用 DataStore 的对象存储，直接存储整个对象。
        viewModelScope.launch {
            val token = userInfoManager.token.firstOrNull()
            userInfo = if (token?.isNotEmpty() == true) {
                UserInfoEntity(token)
            } else {
                null
            }
            baseUrl = userInfoManager.url.firstOrNull().toString()
            account = userInfoManager.account.firstOrNull().toString()
            password = userInfoManager.password.firstOrNull().toString()

            Log.d("--------Login---------", userInfo.toString())
        }
    }

    //是否已登录
    val logged: Boolean
        get() {
            return userInfo != null
        }

    //是否正在登录
    var loading by mutableStateOf(false)
        private set

    var error by mutableStateOf("")
        private set


    /**
     * 儲存 BASEPATH
     *
     */
    suspend fun saveBaseUrl() {  //onClose: () -> Unit
        error = ""
        loading = true
        viewModelScope.launch {
            userInfoManager.saveUrl(baseUrl)
        }
        loading = false
    }

    suspend fun savePassword() {  //onClose: () -> Unit
        viewModelScope.launch {
            userInfoManager.savePassword(account, password)
        }
    }

    suspend fun clearPassword() {  //onClose: () -> Unit
        viewModelScope.launch {
            userInfoManager.clearPassword()
        }
    }


    /**
     * 登录操作
     *
     */
    suspend fun login(onClose: () -> Unit) {  //onClose: () -> Unit
        error = ""
        loading = true
        try {
            val res = userService.signIn(UserService.AuthData(account.replace("\\s".toRegex(), ""), sha256(sha256(password))))
            if (res.isSuccessful) {
                if (res?.body()?.token != null) {
                    userInfo = UserInfoEntity(res?.body()?.token!!)
                    userInfoManager.save(res?.body()?.token!!)
                    onClose()
                }
            }
            else {
                //失败
                val jsonObj = JSONObject(res.errorBody()!!.charStream().readText())
                Log.d("Erreo->Result", " ${jsonObj} " )
                error = jsonObj.toString()
            }
            // handle data
        } catch (exception: Exception) {
            // handle errors
            Log.d("=====Exception", "${exception} ")
            error = exception.message.toString()
        }
        loading = false
    }

    private fun sha256(content: String): String {
        val hash = MessageDigest.getInstance("SHA-256").digest(content.toByteArray())
        val hex = StringBuilder(hash.size * 2)
        for (b in hash) {
            var str = Integer.toHexString(b.toInt())
            if (b < 0x10) {
                str = "0$str"
            }
            hex.append(str.substring(str.length - 2))
        }
        return hex.toString()
    }


    fun clear() {
        viewModelScope.launch {
            userInfoManager.clearLogin() //清除本地数据存储
            userInfo = null //置空内存数据
        }
    }
}