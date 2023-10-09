package com.example.xibo

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding>: AppCompatActivity() {

    private var _binding: VB? = null
    private lateinit var loader: AlertDialog


    val binding: VB
        get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLoading()
        _binding = getViewBinding()
    }
    abstract fun getViewBinding(): VB
    private fun setLoading() {
        val builder1 = AlertDialog.Builder(this)
        builder1.setView(View.inflate(this, R.layout.loader, null))
        builder1.setCancelable(false)
        loader = builder1.create()
        loader.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
    fun showLoading() {
        loader.show()
    }

    fun dismissLoading() {
        loader.dismiss()
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}