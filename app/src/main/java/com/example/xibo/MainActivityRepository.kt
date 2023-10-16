package com.example.xibo

import com.example.xibo.service.ApiService
import com.google.gson.Gson
import com.nej.wms_android.network.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class MainActivityRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun registerDisplay(registerDisplay: RequestBody): SoapResult {
        return withContext(Dispatchers.IO) {
            try{
                val response =
                    apiService.registerDisplay("application/xml", registerDisplay).execute()
                if (response.isSuccessful) {
                    SoapResult.Success(response.body()?.string()?:"")
                } else {
                    SoapResult.Error(response.errorBody()?.string()?:"") // Handle error cases here
                }
            }catch (e: Exception){
                e.printStackTrace()
                SoapResult.Error("Error Occured")
            }
        }
    }
}