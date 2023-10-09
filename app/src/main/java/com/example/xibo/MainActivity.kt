package com.example.xibo

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.xibo.databinding.ActivityMainBinding
import com.example.xibo.requestModel.RegisterDisplayRequest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
//import org.json.XML

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val viewModel: MainActivityViewModel by viewModels()
    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.registerDisplay(
            RegisterDisplayRequest(
                "StqjvluN",
                "12332112332112",
                "PostMan",
                "android",
                "3",
                311,
                "123456",
            )
        )

//        viewModel.registerDisplayResponse.observe(this){ result->
//            when (result) {
//                is SoapResult.Success -> {
//                    val displayInfo = result.displayInfo
//                    Log.e("displayInfo",displayInfo.toString())
//                    // Update the UI or perform other actions
//                }
//                is SoapResult.Error -> {
//                    // Handle error case
//                    val errorMessage = result.message
//                    Log.e("error",errorMessage)
//
//                    // Show an error message to the user or perform error handling
//                }
//            }
//        }
//        CoroutineScope(Dispatchers.Main).launch {
//            try {
//                val response = viewModel.registerDisplay(
//                        RegisterDisplayRequest(
//                    "StqjvluN",
//                    "12332112332111",
//                    "PostMan",
//                    "android",
//                    "3",
//                    311,
//                    "123456",
//                    )
//                )
//// Update the UI with the received data
//            } catch (e: Exception) {
//// Handle the exception
//            }
//        }
//        val soapRequestTask = SoapRequestAsyncTask()
//        soapRequestTask.execute()

}
//        val registerDisplay = RegisterDisplay()
//
//        coroutineScope.launch {
//            val response = registerDisplay.registerDisplay(
//                "StqjvluN",
//                "12332112332111",
//                "PostMan",
//                "android",
//                "3",
//                311,
//                "123456",
//            )
//            val displayInfo = XML.toJSONObject(response)
//        Log.e("display Info",displayInfo.toString())
//        }
//    }
//            val registerDisplayResponse = SoapResponseParser().parseRegisterDisplayResponse(
//                response ?: "",
//                RegisterDisplayResponse::class
//            )
//            Log.e("registerDisplayResponse", registerDisplayResponse.toString())
//        }

    }



