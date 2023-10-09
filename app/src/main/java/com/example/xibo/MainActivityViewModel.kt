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
      /*  val requestBody =
            "<soap:Envelope xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/' xmlns:soapenc='http://schemas.xmlsoap.org/soap/encoding/' xmlns:tns='urn:xmds' xmlns:types='urn:xmds/encodedTypes' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema'><soap:Body soap:encodingStyle='http://schemas.xmlsoap.org/soap/encoding/'><tns:RegisterDisplay>
        <serverKey xsi:type='xsd:string'>StqjvluN</serverKey>
        <hardwareKey xsi:type='xsd:string'>123123345</hardwareKey>
        <displayName xsi:type='xsd:string'>Waqar</displayName>
        <clientType xsi:type='xsd:string'>mobile</clientType>
        <clientVersion xsi:type='xsd:string'>1.0</clientVersion>
        <clientCode xsi:type='xsd:int'>1</clientCode>
        <macAddress xsi:type='xsd:string'>123456</macAddress>
        </tns:RegisterDisplay>
        </soap:Body>
        </soap:Envelope>".trimIndent()*/

        //val requestBody ="""<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/" xmlns:tns="urn:xmds" xmlns:types="urn:xmds/encodedTypes" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema"><soap:Body soap:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"><tns:RegisterDisplay><serverKey xsi:type="xsd:string">${registerDisplayRequest.serverKey}</serverKey><hardwareKey xsi:type="xsd:string">${registerDisplayRequest.hardwareKey}</hardwareKey><displayName xsi:type="xsd:string">${registerDisplayRequest.displayName}</displayName><clientType xsi:type="xsd:string">${registerDisplayRequest.clientType}</clientType><clientVersion xsi:type="xsd:string">${registerDisplayRequest.clientVersion}</clientVersion><clientCode xsi:type="xsd:int">$${registerDisplayRequest.clientCode}</clientCode><macAddress xsi:type="xsd:string">${registerDisplayRequest.macAddress}</macAddress></tns:RegisterDisplay></soap:Body></soap:Envelope>""".trimIndent()
        var requestBody="<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:tns=\"urn:xmds\" xmlns:types=\"urn:xmds/encodedTypes\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><soap:Body soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\"><tns:RegisterDisplay><serverKey xsi:type=\"xsd:string\">${registerDisplayRequest.serverKey}</serverKey><hardwareKey xsi:type=\"xsd:string\">${registerDisplayRequest.hardwareKey}</hardwareKey><displayName xsi:type=\"xsd:string\">${registerDisplayRequest.displayName}</displayName><clientType xsi:type=\"xsd:string\">${registerDisplayRequest.clientType}</clientType><clientVersion xsi:type=\"xsd:string\">${registerDisplayRequest.clientVersion}</clientVersion><clientCode xsi:type=\"xsd:int\">${registerDisplayRequest.clientCode}</clientCode><macAddress xsi:type=\"xsd:string\">${registerDisplayRequest.macAddress}</macAddress></tns:RegisterDisplay></soap:Body></soap:Envelope>"
        //requestBody=requestBody.replace("'",Char(64).toString())
        Log.e("Request Json",(requestBody))
        viewModelScope.launch {
            val response: ResponseBody? = mainActivityRepository.registerDisplay(requestBody)

            if (response != null) {
                val displayInfo = xmlToJson(response.string())
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