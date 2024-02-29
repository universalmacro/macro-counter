package com.example.macrocounter.viewModel


//import okhttp3.internal.and
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
import java.security.MessageDigest


class UserViewModel(context: Context) : ViewModel() {

    private val userInfoManager = UserInfoManager(context)
    private val userService = UserService.instance()

    var account by mutableStateOf("")

    var password by mutableStateOf("")

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
     * 登录操作
     *
     */
    suspend fun login(onClose: () -> Unit) {  //onClose: () -> Unit
        error = ""
        loading = true
//        val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3MDk3MjA5NzUsImp0aSI6IjE3NjI3ODcxMjA5MzE0MDU4MjQiLCJpZCI6IjE3NjI3ODcxMjA5MzE0MDU4MjQiLCJtZXJjaGFudElkIjoiMSIsImFjY291bnQiOiIiLCJ0eXBlIjoiTUFJTiJ9.tupGG71prl5vVoON5hvJfz2-AqSPC-PPvbHxf8_LTOg"
//        userInfo = UserInfoEntity(token)
//        viewModelScope.launch {
//            userInfoManager.save(token)
//
//        }
//        onClose()
        try {
            Log.d("Login", " ${account}: ${md5(md5(password))}" )

            val res = userService.signIn(UserService.AuthData(account, md5(md5(password))))
            Log.d("Login==Result", " ${res}" )

            if (res?.token != null) {
                userInfo = UserInfoEntity(res?.token)
                userInfoManager.save(res?.token)
                onClose()
            } else {
                //失败
                Log.d("Erreo->Result", " ${res.error} " )
                error = res.message
            }
            // handle data
        } catch (exception: Exception) {
            // handle errors
            Log.d("=====Exception", "${exception} ")

        }


        loading = false
    }

    fun md5(content: String): String {
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
            userInfoManager.clear() //清除本地数据存储
            userInfo = null //置空内存数据
        }
    }
}