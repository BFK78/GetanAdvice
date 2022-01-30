package com.example.getanadvice.core

sealed class Resources<T> (val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?): Resources<T>(data = data)
    class Error<T>(data: T? = null, message: String): Resources<T>(data = data,message = message)
    class Loading<T>(data: T? = null): Resources<T>(data = data)
}
