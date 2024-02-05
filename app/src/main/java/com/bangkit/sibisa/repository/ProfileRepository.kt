package com.bangkit.sibisa.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bangkit.sibisa.retrofit.models.ErrorResponse
import com.bangkit.sibisa.retrofit.models.exp.UpdateExpRequest
import com.bangkit.sibisa.retrofit.models.level.UpdateLevelRequest
import com.bangkit.sibisa.retrofit.models.profile.Profile
import com.bangkit.sibisa.retrofit.models.profile.UpdateProfileResponse
import com.bangkit.sibisa.retrofit.models.result.NetworkResult
import com.bangkit.sibisa.retrofit.RetrofitService
import com.google.gson.Gson
import okhttp3.MultipartBody
import retrofit2.HttpException

class ProfileRepository constructor(
    private val GCPService: RetrofitService
) {
    fun getAllUserProfiles(): LiveData<NetworkResult<List<Profile?>?>> = liveData {
        emit(NetworkResult.Loading)
        try {
            val response = GCPService.getAllUserProfiles()

            if (response.status in 599 downTo 400) {
                throw Exception(response.message)
            }

            val data = response.data

            emit(NetworkResult.Success(data))
        } catch (e: Exception) {
            try {
                val errorBody = Gson().fromJson(
                    (e as? HttpException)?.response()?.errorBody()
                        ?.charStream(), ErrorResponse::class.java
                ) ?: null
                emit(NetworkResult.Error(errorBody?.errorCode.toString()))
            } catch (e: Exception) {
                emit(NetworkResult.Error("Server error, please try again later"))
            }
        }
    }

    fun getUserProfile(userID: Int): LiveData<NetworkResult<Profile>> = liveData {
        emit(NetworkResult.Loading)
        try {
            val response = GCPService.getUserProfileById(userID)

            if (response.status in 599 downTo 400) {
                Log.d("USER_PROFILE", response.message)
                throw Exception(response.message)
            }

            val data = response.data!!

            emit(NetworkResult.Success(data))
        } catch (e: Exception) {
            try {
                val errorBody = Gson().fromJson(
                    (e as? HttpException)?.response()?.errorBody()
                        ?.charStream(), ErrorResponse::class.java
                ) ?: null
                emit(NetworkResult.Error(errorBody?.errorCode.toString()))
            } catch (e: Exception) {
                emit(NetworkResult.Error("Server error, please try again later"))
            }
        }
    }

    fun updateUserProfile(
        userID: Int,
        file: MultipartBody.Part,
    ): LiveData<NetworkResult<UpdateProfileResponse>> = liveData {
        emit(NetworkResult.Loading)
        try {
            val response = GCPService.updateProfile(userID, file, null, null)

            if (response.status in 599 downTo 400) {
                throw Exception(response.message)
            }

            val data = response.data!!

            emit(NetworkResult.Success(data))
        } catch (e: Exception) {
            try {
                val errorBody = Gson().fromJson(
                    (e as? HttpException)?.response()?.errorBody()
                        ?.charStream(), ErrorResponse::class.java
                ) ?: null
                emit(NetworkResult.Error(errorBody?.errorCode.toString()))
            } catch (e: Exception) {
                Log.d("CHECK_FINISH", e.message.toString())
                emit(NetworkResult.Error("Server error, please try again later"))
            }
        }
    }

    fun updateExp(expData: UpdateExpRequest): LiveData<NetworkResult<Boolean>> = liveData {
        emit(NetworkResult.Loading)
        try {
            val response = GCPService.updateExp(expData)

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
                Log.d("CHECK_FINISH", e.message.toString())
                emit(NetworkResult.Error("Server error, please try again later"))
            }
        }
    }

    fun updateLevel(levelData: UpdateLevelRequest): LiveData<NetworkResult<Boolean>> = liveData {
        emit(NetworkResult.Loading)
        try {
            val response = GCPService.updateLevel(levelData)

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
                emit(NetworkResult.Error("Server error, please try again later"))
            }
        }
    }
}