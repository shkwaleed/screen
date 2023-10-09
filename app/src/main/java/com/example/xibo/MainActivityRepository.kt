package com.example.xibo

import com.example.xibo.service.ApiService
import com.google.gson.Gson
import com.nej.wms_android.network.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import javax.inject.Inject

class MainActivityRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun registerDisplay(registerDisplay: String): ResponseBody? {
        return withContext(Dispatchers.IO) {
            val response = apiService.registerDisplay("application/xml",registerDisplay).execute()
            if (response.isSuccessful) {
                response.body()
            } else {
                response.errorBody() // Handle error cases here
            }
        }
    }
}