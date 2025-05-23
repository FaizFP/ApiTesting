package com.example.api

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch


import android.app.Activity

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

class MainActivity : Activity() {

    // buat CoroutineScope manual yang akan dibersihkan di onDestroy
    private val activityScope = CoroutineScope(Dispatchers.Main + Job())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activityScope.launch {
            val response = RetrofitInstance.api.fetchData()
            if (response.isSuccessful) {
                Log.d("RETROFIT", "Response: ${response.body()}")
            } else {
                Log.e("RETROFIT", "Error: ${response.code()}")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activityScope.cancel()  // hentikan coroutine saat Activity dihancurkan
    }
}