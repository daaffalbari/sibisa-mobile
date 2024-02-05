package com.bangkit.sibisa.retrofit.models.exp

import com.google.gson.annotations.SerializedName

data class UpdateExpRequest(
    @field:SerializedName("exp")
    val exp: Int? = null
)