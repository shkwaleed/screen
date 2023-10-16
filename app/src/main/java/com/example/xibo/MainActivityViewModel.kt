package com.example.xibo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xibo.requestModel.RegisterDisplayRequest
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import java.util.HashMap
import java.util.Map
import java.util.Properties
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel@Inject constructor(
    private val mainActivityRepository: MainActivityRepository
): ViewModel()  {

    private val _errorResponse = MutableLiveData<String>()
    private val _registerDisplayResponse = MutableLiveData<SoapResult>()
    val registerDisplayResponse: LiveData<SoapResult>
        get() = _registerDisplayResponse

    fun registerDisplay(registerDisplayRequest: RegisterDisplayRequest) {
        Log.e("request",registerDisplayRequest.toString())
        val requestBody = """
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
    xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/"
    xmlns:tns="urn:xmds" xmlns:types="urn:xmds/encodedTypes"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:xsd="http://www.w3.org/2001/XMLSchema">
    <soap:Body soap:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
        <tns:RegisterDisplay>
            <serverKey xsi:type="xsd:string">StqjvluN</serverKey>
            <hardwareKey xsi:type="xsd:string">12332112332112</hardwareKey>
            <displayName xsi:type="xsd:string">PostMan</displayName>
            <clientType xsi:type="xsd:string">android</clientType>
            <clientVersion xsi:type="xsd:string">3</clientVersion>
            <clientCode xsi:type="xsd:int">311</clientCode>
            <macAddress xsi:type="xsd:string">123456</macAddress>
        </tns:RegisterDisplay>
    </soap:Body>
</soap:Envelope>
""".trimIndent()
        val request = requestBody.toRequestBody("application/xml".toMediaTypeOrNull())
//        Log.e("Request Json",(xmlToJson(requestBody)))
        viewModelScope.launch {
            val response: SoapResult = mainActivityRepository.registerDisplay(request)

            if (response != null) {
                val displayInfo = xmlToJson(response.toString())

                _registerDisplayResponse.value = SoapResult.Success(displayInfo)
                Log.e("display Info",displayInfo.toString())
//                val result = SoapResponseParser().parseRegisterDisplayResponse(response.string())
//                _registerDisplayResponse.value = displayInfo                // Handle the SOAP response here
            } else {
                _registerDisplayResponse.value = SoapResult.Error("Failed to make the SOAP request.")

                // Handle error cases
            }
        }
    }
    fun xmlToJson(xmlString: String): String {
        val xmlMapper = XmlMapper()
        val jsonObject = xmlMapper.readValue(xmlString, Object::class.java)
        val jsonMapper = ObjectMapper()
        return jsonMapper.writeValueAsString(jsonObject)
    }
}