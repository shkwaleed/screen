package com.nej.wms_android.network

sealed class NetworkResult<T> {
    data class Loading<T>(val isLoading: Boolean) : NetworkResult<T>()
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Failure<T>(val errorMessage: T) : NetworkResult<T>()
}
