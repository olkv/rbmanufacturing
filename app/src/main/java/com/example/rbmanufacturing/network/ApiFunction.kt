package com.example.rbmanufacturing.network

import android.content.Context
import android.os.Build
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService

//Функция возвращающая URL соединение к сервису
fun getURLConnection(context: Context): String {

    val sharedPreferences = context.getSharedPreferences("RbManConfig",Context.MODE_PRIVATE)

    val urlConnect = sharedPreferences?.getString("urlConnect","http://31.25.243.2/")!!
    val baseName = sharedPreferences.getString("baseName","ERP_RB0")!!

    return "$urlConnect$baseName/hs/manf/"
}

//Функция возвращающая имя позльзователя
fun getUserName(context: Context): String {

    val sharedPreferences = context.getSharedPreferences("RbManConfig", Context.MODE_PRIVATE)

    return sharedPreferences?.getString("userName", "oleg")!!
}

fun getIdTelephone(context: Context): String {
    val IMEI = ""

    return IMEI
}



