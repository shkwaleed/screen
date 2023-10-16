package com.example.xibo

import android.util.Log
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody


class APIService {

    private val NAMESPACE = "urn:xmds"
    private val METHOD_NAME = "RegisterDisplay"
    private val SOAP_ACTION = "$NAMESPACE#$METHOD_NAME"
    private val URL = "https://xibo.decimalspace.com/xmds.php?v=6&method=registerDisplay"

    suspend fun registerDisplay(
        serverKey: String,
        hardwareKey: String,
        displayName: String,
        clientType: String,
        clientVersion: String,
        clientCode: Int,
        macAddress: String
    ): String? {
        return withContext(Dispatchers.IO) {
            try {
                val URL = "https://xibo.decimalspace.com/xmds.php?v=6&method=registerDisplay"

                // Define the SOAP request body
                val soapRequestBody = """
                <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
                    xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/"
                    xmlns:tns="urn:xmds" xmlns:types="urn:xmds/encodedTypes"
                    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                    xmlns:xsd="http://www.w3.org/2001/XMLSchema">
                    <soap:Body soap:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
                        <tns:RegisterDisplay>
                            <serverKey xsi:type="xsd:string">$serverKey</serverKey>
                            <hardwareKey xsi:type="xsd:string">$hardwareKey</hardwareKey>
                            <displayName xsi:type="xsd:string">$displayName</displayName>
                            <clientType xsi:type="xsd:string">$clientType</clientType>
                            <clientVersion xsi:type="xsd:string">$clientVersion</clientVersion>
                            <clientCode xsi:type="xsd:int">$clientCode</clientCode>
                            <macAddress xsi:type="xsd:string">$macAddress</macAddress>
                        </tns:RegisterDisplay>
                    </soap:Body>
                </soap:Envelope>
            """.trimIndent()

                // Create an OkHttpClient
                val client = OkHttpClient()

                // Create a RequestBody with the SOAP request body and set the Content-Type header
                val requestBody = soapRequestBody.toRequestBody("application/xml".toMediaTypeOrNull())

                // Create a POST request with the URL and RequestBody
                val request = Request.Builder()
                    .url(URL)
                    .post(requestBody)
                    .addHeader("Content-Type", "application/xml")
                    .build()

                Log.e("Request", "Request: " + soapRequestBody)
                // Execute the request and get the response
                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    val responseBody = xmlToJson(response.body?.string()?:"")
//                    val activationMessage = extractDataFromJson(responseBody)
//                    val extractData = extractData(responseBody)
                    Log.e("Response Json", responseBody)

                    return@withContext responseBody
                }
                else {
                    // Handle error cases
                    return@withContext null
                }
            } catch (e: IOException) {
                e.printStackTrace()
                return@withContext null
            }
            return@withContext null
        }
    }

    suspend fun requiredFiles(
        serverKey: String,
        hardwareKey: String
    ): String? {
        return withContext(Dispatchers.IO) {
            try {
                val URL = "https://xibo.decimalspace.com/xmds.php?v=6&method=RequiredFiles"

                // Define the SOAP request body
                val soapRequestBody = """
                <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
                    xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/"
                    xmlns:tns="urn:xmds" xmlns:types="urn:xmds/encodedTypes"
                    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                    xmlns:xsd="http://www.w3.org/2001/XMLSchema">
                    <soap:Body soap:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
                        <tns:RequiredFiles>
                            <serverKey xsi:type="xsd:string">$serverKey</serverKey>
                            <hardwareKey xsi:type="xsd:string">$hardwareKey</hardwareKey>
                        </tns:RequiredFiles>
                    </soap:Body>
                </soap:Envelope>
            """.trimIndent()

                // Create an OkHttpClient
                val client = OkHttpClient()

                // Create a RequestBody with the SOAP request body and set the Content-Type header
                val requestBody = soapRequestBody.toRequestBody("application/xml".toMediaTypeOrNull())

                // Create a POST request with the URL and RequestBody
                val request = Request.Builder()
                    .url(URL)
                    .post(requestBody)
                    .addHeader("Content-Type", "application/xml")
                    .build()

                Log.e("Request", "Request: " + soapRequestBody)
                // Execute the request and get the response
                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    val responseBody = xmlToJson(response.body?.string()?:"")
//                    val activationMessage = extractDataFromJson(responseBody)
//                    val extractData = extractData(responseBody)
                    Log.e("Response Json", responseBody)

                    return@withContext responseBody
                }
                else {
                    // Handle error cases
                    return@withContext null
                }
            } catch (e: IOException) {
                e.printStackTrace()
                return@withContext null
            }
            return@withContext null
        }
    }

    fun xmlToJson(xmlString: String): String {
        val xmlMapper = XmlMapper()
        val jsonObject = xmlMapper.readValue(xmlString, Object::class.java)
        val jsonMapper = ObjectMapper()
        return jsonMapper.writeValueAsString(jsonObject)
    }

}


