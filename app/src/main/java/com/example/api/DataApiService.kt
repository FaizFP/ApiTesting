package com.example.api

import retrofit2.Response
import retrofit2.http.GET

interface DataApiService {
    @GET("posts")
    suspend fun fetchData(): Response<String>
}