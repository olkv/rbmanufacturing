package com.example.rbmanufacturing.network

import android.util.Log
import com.example.rbmanufacturing.domain.models.CItemWarehouse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun getVersionManf() {

    var mService = Common.retrofitService

    mService.getVersionProtocol().enqueue(object : Callback<String> {
        override fun onFailure(call: Call<String>, t: Throwable) {
            Log.d("MYLOG","Ошибка получения данных. ${t.message}")
        }

        override fun onResponse(call: Call<String>, response: Response<String>) {

            val body = response.body() as String

            Log.d("MYLOG","Данные GET запроса получены")
            Log.d("MYLOG", "Версия протокола : $body")

        }

    })
}


fun getListWarehouse(username: String) {

    var mService = Common.retrofitService

    Log.d("MYLOG","Вход в процедуру")

    mService.getListWarehouseManf(username = username).enqueue(object :
        Callback<MutableList<CItemWarehouse>> {
        override fun onFailure(call: Call<MutableList<CItemWarehouse>>, t: Throwable) {
            Log.d("MYLOG","Ошибка получения")
        }

        override fun onResponse(call: Call<MutableList<CItemWarehouse>>, response: Response<MutableList<CItemWarehouse>>) {

            val body = response.body() as MutableList<CItemWarehouse>

            Log.d("MYLOG","Данные GET запроса получены")
            Log.d("MYLOG", "Количество строк : ${body.size.toString()}")

            body.forEach { item ->
                Log.d("MYLOG", "name =  ${item.name}  |  uid = ${item.uid} ")
            }

        }
    })

    Log.d("MYLOG","Выход из процедуру")
}


