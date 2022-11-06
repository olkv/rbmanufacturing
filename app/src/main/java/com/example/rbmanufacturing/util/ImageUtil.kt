package com.example.rbmanufacturing.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

class ImageUtil {

    fun convert(base64str: String): Bitmap {
        val decodeBytes = Base64.decode(base64str.substring(base64str.indexOf(","+1)),Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodeBytes,0,decodeBytes.size)
    }

    fun convert(bitmap: Bitmap): String {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream)

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
    }

}