package com.arthur.marvelapp.api.entity

import com.arthur.marvelapp.model.Data

data class HeroeResponse(
    val attributionHTML: String,
    val attributionText: String,
    val code: Int,
    val copyright: String,
    val data: Data,
    val etag: String,
    val status: String
)