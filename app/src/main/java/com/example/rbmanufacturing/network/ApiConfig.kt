package com.example.rbmanufacturing.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import java.util.prefs.Preferences


//Общий объект для работы с Retrofit2, создает объект и подключает конвертор JSON
object RetrofitClient {
    private var retrofit: Retrofit? = null

    fun getClient(baseUrl: String): Retrofit {

        //Настраиваем время отклика ссоединения
        val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()

        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .callFactory(okHttpClient)
                .build()
        }
        return retrofit!!
    }
}


//соединяем описание интерфейсов с ретрофит объектом и указаем конкретной строки URL для работы с серфисами
object Common {
    //private const val BASE_URL = "http://31.25.243.2/ERP_RB0/hs/manf/"
    private const val BASE_URL = "http://31.25.243.2/erp_r/hs/manf/"

    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
}


