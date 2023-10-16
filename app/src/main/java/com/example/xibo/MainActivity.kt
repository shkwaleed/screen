package com.example.xibo

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.xibo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.w3c.dom.Document
import org.xml.sax.InputSource
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory


//import org.json.XML

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val viewModel: MainActivityViewModel by viewModels()
    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    var registerDisplayResponse = MutableLiveData< Map<String, String>>()
    var requiredFilesResponse = MutableLiveData< List<Map<String, String>>>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()

        binding.registerDisplay.setOnClickListener {
            binding.displayName.clearFocus()
            registerDisplayCall()
        }
        registerDisplayResponse.observe(this){ data->
            if(data.isEmpty()){
                SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Oops...")
                    .setContentText("Something went wrong").show()
            }else {
                Log.e("RegisterDisplay",data.toString())
                SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Success")
                    .setContentText(data["message"])
                    .setConfirmClickListener {
                        if(data["code"] == "READY"){
                            requiredFilesCall()
                        }
                    }
                    .show()

            }
            requiredFilesResponse.observe(this){data->
                if(data.isEmpty()){
                    SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Oops...")
                        .setContentText("Something went wrong").show()
                }else{
                    Log.e("RequiredFiles",data.toString())
                }
            }
        }
    }

    private fun initView() {
    }

    @SuppressLint("HardwareIds")
    fun getAndroidId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }
    @SuppressLint("SetTextI18n")
    fun registerDisplayCall() {
        if(binding.displayName.text?.isNotEmpty() == true
        ) {
            showLoading()
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val responseData = APIService().registerDisplay(
                        "StqjvluN",
                        "12332112332111",//Build.MODEL,
                        "PostMan",//binding.displayName.text.toString(),
                        "Android",
                        "3",
                        311,
                        "123456"//getAndroidId(this@MainActivity)
                    )
                    val extractData = extractData(responseData ?: "")
                    val data =
                        "Date: ${extractData["date"]}\nTimeZone: ${extractData["timezone"]}\nStatus: ${extractData["status"]}\nCode: ${extractData["code"]}\nMessage: ${extractData["message"]}\nCheckSchedule: ${extractData["checkSchedule"]}\nCheckRf: ${extractData["checkRf"]}"
                    if (extractData.isNotEmpty()) {
                        registerDisplayResponse.value = extractData
                    }else{
                        registerDisplayResponse.value = mapOf()
                    }
                } catch (e: Exception) {
                    // Handle any exceptions that may occur during the API call
                    e.printStackTrace()
                    registerDisplayResponse.value = mapOf()
                } finally {
                    // Hide the loading indicator, whether the API call succeeds or fails
                    dismissLoading()
                }
            }
        }else{
            binding.displayName.error = "Display Name Can't be Empty"
        }
    }
    fun requiredFilesCall() {
        showLoading()
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val responseData = APIService().requiredFiles(
                    "StqjvluN",
                    "12332112332111"//Build.MODEL,
                )
                val extractData = ExtractionUtil().parseRequiredFilesResponse(responseData ?: "")
                if (extractData.isNotEmpty()) {
                    requiredFilesResponse.value = extractData
                    Log.e("RequiredFilesResponse",extractData.toString())
                }else{
                    requiredFilesResponse.value = listOf(mapOf())
                }
            } catch (e: Exception) {
                // Handle any exceptions that may occur during the API call
                e.printStackTrace()
                requiredFilesResponse.value = listOf(mapOf())
            } finally {
                // Hide the loading indicator, whether the API call succeeds or fails
                dismissLoading()
            }
        }
    }

//    private fun updateTextView(data: String) {
//        withContext(Dispatchers.Main){
//            // Log for debugging
//            Log.d("MainActivity", "Updating TextView with data: $data")
//            binding.response.text = data
//        }
//    }
    fun extractData(jsonString: String): Map<String, String> {
        try {
            val jsonObject = JSONObject(jsonString)
            val bodyObject = jsonObject.getJSONObject("Body")
            val registerDisplayResponseObject = bodyObject.getJSONObject("RegisterDisplayResponse")
            val activationMessageObject = registerDisplayResponseObject.getJSONObject("ActivationMessage")
            val xmlString = activationMessageObject.getString("")

            val xmlDocument = parseXml(xmlString)
            val attributes = xmlDocument.documentElement.attributes

            val extractedData = mutableMapOf<String, String>()

            for (i in 0 until attributes.length) {
                val attribute = attributes.item(i)
                extractedData[attribute.nodeName] = attribute.nodeValue
            }

            return extractedData
        } catch (e: Exception) {
            // Handle exceptions here
            e.printStackTrace()
            return emptyMap() // or return some default value or throw a custom exception
        }
    }
    fun showNeutralDialog(context: Context, title: String, message: String, neutralButtonText: String) {
        val builder = AlertDialog.Builder(context)

        builder.setTitle(title)
        builder.setMessage(message)

        builder.setNeutralButton(neutralButtonText) { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        val window = dialog.window
        val layoutParams = window?.attributes
        layoutParams?.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams?.gravity = Gravity.CENTER
        window?.attributes = layoutParams

        dialog.show()
    }

    fun parseXml(xmlString: String): Document {
        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()
        val inputSource = InputSource(StringReader(xmlString))
        return builder.parse(inputSource)
    }

}



