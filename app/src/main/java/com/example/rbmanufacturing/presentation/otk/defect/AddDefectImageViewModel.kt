package com.example.rbmanufacturing.presentation.otk.defect

import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import com.example.rbmanufacturing.util.ImageUtil

class AddDefectImageViewModel:ViewModel() {

    fun getBase64Image(imageUri:String):String {

        val bitmap = BitmapFactory.decodeFile(imageUri)
        val strImage = ImageUtil.convert(bitmap)

        return strImage

    }


}