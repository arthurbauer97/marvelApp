package com.arthur.marvelapp.api.entity

import com.arthur.marvelapp.model.Data

data class Response(
    val code: Int,
    val etag: String,
    val data: Data
)