package com.example.rbmanufacturing.presentation.docmaster

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DocMasterViewModelFactory(private val uid: String, private val urlConnection: String): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DocMasterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DocMasterViewModel(uid, urlConnection) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }

}