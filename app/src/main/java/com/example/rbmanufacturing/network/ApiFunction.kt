package com.example.rbmanufacturing.network

import android.content.Context


fun getURLConnection(context: Context): String {

    val sharedPreferences = context.getSharedPreferences("RbManConfig",Context.MODE_PRIVATE)

    val urlConnect = sharedPreferences?.getString("urlConnect","http://31.25.243.2/")!!
    val baseName = sharedPreferences?.getString("baseName","ERP_RB0")!!

    return "$urlConnect$baseName/hs/manf/"
}

fun getUserName(context: Context): String {

    val sharedPreferences = context.getSharedPreferences("RbManConfig", Context.MODE_PRIVATE)

    return sharedPreferences?.getString("userName", "oleg")!!
}





