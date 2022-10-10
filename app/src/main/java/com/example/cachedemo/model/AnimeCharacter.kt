package com.example.cachedemo.model


data class AnimeCharacter<T>(
    var info: Info,
    var results: List<T>
)

data class RequestState<T>(
    val error: ApiError? = null,
    val progress: Boolean = false,
    val apiResponse: AnimeCharacter<T>? = null,
    val apiCustomResponse:T?=null
)

data class ApiError(val errorState: String, val customMessage: String?)