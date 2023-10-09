package com.example.xibo.network

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context) {
    val PREFS_FILENAME = "com.nej.wms_android.prefs"

    private val BAREER = "bareer"
    private val WAREHOUSE = "warehouse"
    private val PRINTER_IP = "printer_ip"
    private val PRINTER_PORT = "printer_port"

    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)


    var token: String
        get() = prefs.getString(BAREER, "")!!
        set(value) = prefs.edit().putString(BAREER, value).apply()

    var ip: String?
        get() = prefs.getString(PRINTER_IP,"")
        set(value) = prefs.edit().putString(PRINTER_IP, value).apply()

    var port: String?
        get() = prefs.getString(PRINTER_PORT,"")
        set(value) = prefs.edit().putString(PRINTER_PORT, value).apply()

    fun clear() {
        prefs.edit().clear()
    }
}