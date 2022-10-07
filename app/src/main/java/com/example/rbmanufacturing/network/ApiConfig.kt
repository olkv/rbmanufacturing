package com.example.rbmanufacturing.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//Общий объект для работы с Retrofit2, создает объект и подключает конвертор JSON
object RetrofitClient {
    private var retrofit: Retrofit? = null

    fun getClient(baseUrl: String): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}


//соединяем описание интерфейсов с ретрофит объектом и указаем конкретной строки URL для работы с серфисами
object Common {
    private const val BASE_URL = "http://31.25.243.2/ERP_RB0/hs/manf/"

    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
}


