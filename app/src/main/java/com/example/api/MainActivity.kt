package com.example.api

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import android.widget.TextView


import android.app.Activity

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

class MainActivity : Activity() {

    private val activityScope = CoroutineScope(Dispatchers.Main + Job())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textResult = findViewById<TextView>(R.id.textResult)

        activityScope.launch {
            try {
                val response = RetrofitInstance.api.fetchData()
                if (response.isSuccessful) {
                    val data = response.body().toString()
                    textResult.text = data
                } else {
                    textResult.text = "Error: ${response.code()}"
                    Log.e("RETROFIT", "Error: ${response.code()}")
                }
            } catch (e: Exception) {
                textResult.text = "Exception: ${e.message}"
                Log.e("RETROFIT", "Exception", e)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activityScope.cancel()
    }
}