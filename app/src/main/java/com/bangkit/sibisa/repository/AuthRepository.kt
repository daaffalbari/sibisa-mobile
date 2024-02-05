package com.bangkit.sibisa.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bangkit.sibisa.retrofit.models.ErrorResponse
import com.bangkit.sibisa.retrofit.models.login.LoginRequest
import com.bangkit.sibisa.retrofit.models.register.RegisterRequest
import com.bangkit.sibisa.retrofit.models.result.NetworkResult
import com.bangkit.sibisa.retrofit.RetrofitService
import com.google.gson.Gson
import retrofit2.HttpException
import kotlin.math.log

class AuthRepository(private val retrofitService: RetrofitService) {
    fun makeLoginRequest(loginData: LoginRequest): LiveData<NetworkResult<String?>> = liveData {
        emit(NetworkResult.Loading)

        try {
            val response = retrofitService.login(loginData)

            if (response.status in 599 downTo 400) {
                throw Exception(response.message)
            }

            val token = response.data?.token

            emit(NetworkResult.Success(token))
            Log.d("auth error", token.toString())
        } catch (e: Exception) {
            try {
                val errorBody = Gson().fromJson(
                    (e as? HttpException)?.response()?.errorBody()
                        ?.charStream(), ErrorResponse::class.java
                ) ?: null
                emit(NetworkResult.Error(errorBody?.errorCode.toString()))
                Log.d("auth error", errorBody?.errorCode.toString())
            } catch (e: Exception) {
                emit(NetworkResult.Error("Server error, please try again later"))
                Log.d("auth error", e.message.toString())
            }
        }
    }

    fun makeRegisterRequest(registerData: RegisterRequest): LiveData<NetworkResult<Boolean>> =
        liveData {
            emit(NetworkResult.Loading)
            try {
                val response = retrofitService.register(registerData)

                if (response.status in 599 downTo 400) {
                    throw Exception(response.message)
                }

                emit(NetworkResult.Success(true))
            } catch (e: Exception) {
                try {
                    val errorBody = Gson().fromJson(
                        (e as? HttpException)?.response()?.errorBody()
                            ?.charStream(), ErrorResponse::class.java
                    ) ?: null
                    emit(NetworkResult.Error(errorBody?.errorCode.toString()))
                } catch (e: Exception) {
                    emit(NetworkResult.Error("Server error, please try again later "))
                    Log.d("auth error", e.toString() )
                }
            }
        }
}