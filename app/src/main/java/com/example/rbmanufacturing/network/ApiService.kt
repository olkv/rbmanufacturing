package com.example.rbmanufacturing.network

import okhttp3.Response
import retrofit2.http.GET

interface ApiService {
    @GET("api/recipes")
    fun getService(): Response

}