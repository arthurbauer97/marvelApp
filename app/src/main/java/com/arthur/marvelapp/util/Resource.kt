package com.arthur.marvelapp.util

open class Resource<T>(
    val data: T?,
    val error: String? = null
)

class SuccessResource<T>(
    data: T
) : Resource<T>(data)

class ErrorResource<T>(
    error: String
) : Resource<T>(data = null, error = error)
