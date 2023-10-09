package com.example.xibo.requestModel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class RegisterDisplayRequest(
    var serverKey: String = "",
    var hardwareKey: String = "",
    var displayName: String = "",
    var clientType: String = "",
    var clientVersion: String = "",
    var clientCode: Int = 0,
    var macAddress: String = "",
    )
