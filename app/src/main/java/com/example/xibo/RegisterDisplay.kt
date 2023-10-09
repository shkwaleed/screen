package com.example.xibo
import android.os.AsyncTask

import android.util.Log
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import io.reactivex.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException
import io.reactivex.schedulers.Schedulers
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Response
import org.json.XML
import retrofit2.converter.simplexml.SimpleXmlConverterFactory


class RegisterDisplay {

    private val NAMESPACE = "urn:xmds"
    private val METHOD_NAME = "RegisterDisplay"
    private val SOAP_ACTION = "$NAMESPACE#$METHOD_NAME"
    private val URL = "https://xibo.decimalspace.com/xmds.php?v=6&method=registerDisplay"

//    suspend fun registerDisplayAsync(
//        serverKey: String,
//        hardwareKey: String,
//        displayName: String,
//        clientType: String,
//        clientVersion: String,
//        clientCode: Int,
//        macAddress: String
//    ): String? {
//        return withContext(Dispatchers.IO) {
//            try {
//                val request = SoapObject(NAMESPACE, METHOD_NAME)
//
//                // Add parameters to the request (same as before)
//                val serverKeyProp = PropertyInfo()
//                serverKeyProp.name = "serverKey"
//                serverKeyProp.type = PropertyInfo.STRING_CLASS
//                serverKeyProp.value = serverKey
//                request.addProperty(serverKeyProp)
//
//                val hardwareKeyProp = PropertyInfo()
//                hardwareKeyProp.name = "hardwareKey"
//                hardwareKeyProp.type = PropertyInfo.STRING_CLASS
//                hardwareKeyProp.value = hardwareKey
//                request.addProperty(hardwareKeyProp)
//
//                val displayNameProp = PropertyInfo()
//                displayNameProp.name = "displayName"
//                displayNameProp.type = PropertyInfo.STRING_CLASS
//                displayNameProp.value = displayName
//                request.addProperty(displayNameProp)
//
//                val clientTypeProp = PropertyInfo()
//                clientTypeProp.name = "clientType"
//                clientTypeProp.type = PropertyInfo.STRING_CLASS
//                clientTypeProp.value = clientType
//                request.addProperty(clientTypeProp)
//
//                val clientVersionProp = PropertyInfo()
//                clientVersionProp.name = "clientVersion"
//                clientVersionProp.type = PropertyInfo.STRING_CLASS
//                clientVersionProp.value = clientVersion
//                request.addProperty(clientVersionProp)
//
//                val clientCodeProp = PropertyInfo()
//                clientCodeProp.name = "clientCode"
//                clientCodeProp.type = PropertyInfo.INTEGER_CLASS
//                clientCodeProp.value = clientCode
//                request.addProperty(clientCodeProp)
//
//                val macAddressProp = PropertyInfo()
//                macAddressProp.name = "macAddress"
//                macAddressProp.type = PropertyInfo.STRING_CLASS
//                macAddressProp.value = macAddress
//                request.addProperty(macAddressProp)
//
//                val envelope = SoapSerializationEnvelope(SoapEnvelope.VER12)
//                envelope.setOutputSoapObject(request)
//                envelope.encodingStyle = "http://schemas.xmlsoap.org/soap/encoding/"
//                envelope.dotNet = true // Not .NET-based
//
//
////                Log.e("bodyIn",envelope.bodyIn.toString())
//
//                Log.e("Envelope Version", "Envelope Version: " + envelope.version)
//                Log.e("Request Name", "Request Name: " + request.name)
//                Log.e("Request NameSpace", "Request NameSpace: " + request.namespace)
//                Log.e("ImplicitTypes", "ImplicitTypes: " + envelope.implicitTypes.toString())
//                Log.e("bodyOut", "BodyOut: " + envelope.bodyOut.toString())
//                Log.e("enc", "enc: " + envelope.enc)
//                Log.e("env", "env: " + envelope.env)
//                Log.e("encodingStyle", "encodingStyle: " + envelope.encodingStyle.toString())
//                Log.e("xsd", "xsd: " + envelope.xsd)
//                Log.e("xsi", "xsi: " + envelope.xsi)
//                Log.e("Request", "Request: " + request.toString())
//                Log.e("URL", "URL: " + URL)
//                val androidHttpTransport = HttpTransportSE(URL)
//                androidHttpTransport.debug = true
//                androidHttpTransport.call(SOAP_ACTION, envelope)
//                val response = envelope.response.toString()
//                Log.e("Request", "Request: " + androidHttpTransport.requestDump)
//                Log.e("Response", "Response: " + androidHttpTransport.responseDump)
//
//
//                Log.e("Response", response)
//
//                response
//            } catch (e: Exception) {
//                e.printStackTrace()
//                null
//            }
//        }
//    }

//    suspend fun registerDisplay(
//        serverKey: String,
//        hardwareKey: String,
//        displayName: String,
//        clientType: String,
//        clientVersion: String,
//        clientCode: Int,
//        macAddress: String
//    ): String? {
//        return withContext(Dispatchers.IO) {
//            try {
//                val URL = "https://xibo.decimalspace.com/xmds.php?v=6&method=registerDisplay"
//
//                // Define the SOAP request body
//                val soapRequestBody = """
//                <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
//                    xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/"
//                    xmlns:tns="urn:xmds" xmlns:types="urn:xmds/encodedTypes"
//                    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
//                    xmlns:xsd="http://www.w3.org/2001/XMLSchema">
//                    <soap:Body soap:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
//                        <tns:RegisterDisplay>
//                            <serverKey xsi:type="xsd:string">$serverKey</serverKey>
//                            <hardwareKey xsi:type="xsd:string">$hardwareKey</hardwareKey>
//                            <displayName xsi:type="xsd:string">$displayName</displayName>
//                            <clientType xsi:type="xsd:string">$clientType</clientType>
//                            <clientVersion xsi:type="xsd:string">$clientVersion</clientVersion>
//                            <clientCode xsi:type="xsd:int">$clientCode</clientCode>
//                            <macAddress xsi:type="xsd:string">$macAddress</macAddress>
//                        </tns:RegisterDisplay>
//                    </soap:Body>
//                </soap:Envelope>
//            """.trimIndent()
//
//                // Create an OkHttpClient
//                val client = OkHttpClient()
//
//                // Create a RequestBody with the SOAP request body and set the Content-Type header
//                val requestBody =
//                    RequestBody.create(MediaType.parse("application/xml"), soapRequestBody)
//
//                // Create a POST request with the URL and RequestBody
//                val request = Request.Builder()
//                    .url(URL)
//                    .post(requestBody)
//                    .addHeader("Content-Type", "application/xml")
//                    .build()
//
//                Log.e("Request", "Request: " + soapRequestBody)
//                // Execute the request and get the response
//                val response = client.newCall(request).execute()
//
//                Log.e("Response", response.body()?.string() ?: "")
//
//                if (response.isSuccessful) {
//                    return@withContext response.body()?.string()
//                } else {
//                    // Handle error cases
//                    return@withContext null
//                }
//            } catch (e: IOException) {
//                e.printStackTrace()
//                return@withContext null
//            }
//            return@withContext null
//        }
//    }

//    fun registerDisplay(
//        serverKey: String,
//        hardwareKey: String,
//        displayName: String,
//        clientType: String,
//        clientVersion: String,
//        clientCode: Int,
//        macAddress: String
//    ): Observable<String?> {
//        return Observable.create { emitter ->
//            try {
//                val URL = "https://xibo.decimalspace.com/xmds.php?v=6&method=registerDisplay"
//
//                // Define the SOAP request body
//                val soapRequestBody = """
//                <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
//                    xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/"
//                    xmlns:tns="urn:xmds" xmlns:types="urn:xmds/encodedTypes"
//                    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
//                    xmlns:xsd="http://www.w3.org/2001/XMLSchema">
//                    <soap:Body soap:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
//                        <tns:RegisterDisplay>
//                            <serverKey xsi:type="xsd:string">$serverKey</serverKey>
//                            <hardwareKey xsi:type="xsd:string">$hardwareKey</hardwareKey>
//                            <displayName xsi:type="xsd:string">$displayName</displayName>
//                            <clientType xsi:type="xsd:string">$clientType</clientType>
//                            <clientVersion xsi:type="xsd:string">$clientVersion</clientVersion>
//                            <clientCode xsi:type="xsd:int">$clientCode</clientCode>
//                            <macAddress xsi:type="xsd:string">$macAddress</macAddress>
//                        </tns:RegisterDisplay>
//                    </soap:Body>
//                </soap:Envelope>
//            """.trimIndent()
//
//                // Create an OkHttpClient
//                val client = OkHttpClient()
//
//                // Create a RequestBody with the SOAP request body and set the Content-Type header
//                val requestBody =
//                    RequestBody.create(MediaType.parse("application/xml"), soapRequestBody)
//
//                // Create a POST request with the URL and RequestBody
//                val request = Request.Builder()
//                    .url(URL)
//                    .post(requestBody)
//                    .addHeader("Content-Type", "application/xml")
//                    .build()
//
//                Log.e("Request", "Request: " + soapRequestBody)
//
//                // Execute the request asynchronously using RxJava's Schedulers.io()
//                client.newCall(request).enqueue(object : Callback {
//                    override fun onResponse(call: Call, response: Response) {
//                        if (response.isSuccessful) {
//                            emitter.onNext(response.body()?.string())
//                        } else {
//                            emitter.onError(Throwable("HTTP Error: ${response.code()}"))
//                        }
//                        emitter.onComplete()
//                    }
//
//                    override fun onFailure(call: Call, e: IOException) {
//                        emitter.onError(e)
//                        emitter.onComplete()
//                    }
//                })
//            } catch (e: Exception) {
//                emitter.onError(e)
//                emitter.onComplete()
//            }
//        }.subscribeOn(Schedulers.io())
//    }
}
class SoapRequestAsyncTask : AsyncTask<Unit, Unit, String?>() {

    // Replace these values with your actual SOAP request parameters
    private val serverKey = "StqjvluN"
    private val hardwareKey = "12332112332110"
    private val displayName = "PostMan"
    private val clientType = "mobile"
    private val clientVersion = "1.0"
    private val clientCode = 1
    private val macAddress = "123456"


    override fun doInBackground(vararg params: Unit?): String? {
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
            val requestBody = RequestBody.create("application/xml".toMediaTypeOrNull(), soapRequestBody)

            // Create a POST request with the URL and RequestBody
            val request = Request.Builder()
                .url(URL)
                .post(requestBody)
                .addHeader("Content-Type", "application/xml")
                .build()

            Log.e("Request","Request: "+soapRequestBody)
            // Execute the request and get the response
            val response = client.newCall(request).execute()

            val registerDisplayResponse = convertXmlStringToJson(response.body?.string()?:"")
            // Check if the request was successful
            Log.e("registerDisplayResponse", registerDisplayResponse.toString())
            if (response.isSuccessful) {
                Log.e("Response",response.body?.string()?:"")
                return xmlToJson(response.body?.string()?:"")
            } else {
                // Handle error cases
                return null
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }
    fun xmlToJson(xmlString: String): String {
        val xmlMapper = XmlMapper()
        val jsonObject = xmlMapper.readValue(xmlString, Object::class.java)
        val jsonMapper = ObjectMapper()
        return jsonMapper.writeValueAsString(jsonObject)
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        if (result != null) {
            // Handle the SOAP response
            Log.d("SOAP Response", result)
        } else {
            // Handle the error
            Log.e("SOAP Request", "Error in SOAP request")
        }
    }
}

