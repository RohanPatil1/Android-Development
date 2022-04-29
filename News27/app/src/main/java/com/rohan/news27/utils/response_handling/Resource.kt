package com.rohan.news27.utils.response_handling

sealed class Resource<T>(val data: T? = null, val message: String? = null) {

    class Success<T>(data: T?) : Resource<T>(data = data)

    class Loading<T>(message: String?) : Resource<T>()

    class Error<T>(message: String?) : Resource<T>(message = message)

}