package com.bangkit.sibisa.retrofit.models.question

import com.google.gson.annotations.SerializedName

data class Question(
    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("question")
    val question: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("question_type")
    val questionType: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("question_level")
    val questionLevel: Int? = null,

    @field:SerializedName("id")
    val id: Int? = null
)
