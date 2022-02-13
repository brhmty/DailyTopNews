package com.example.dailytopnews.domain.util

sealed class Resource<T>(
    val data : T? = null,
    val error : String? = null
){
    class Success<T> (data : T) : Resource<T>(data = data)
    class Failure<T> (error : String) : Resource<T>(error = error)
    class Loading<T> () : Resource<T>()
}
