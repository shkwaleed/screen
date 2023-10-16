package com.example.xibo.service

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("xmds.php?v=6&method=registerDisplay")
    suspend fun registerDisplay(
        @Header ("Content-Type") contentType:String,
        @Body registerDisplayRequest: RequestBody): Call<ResponseBody>


}
