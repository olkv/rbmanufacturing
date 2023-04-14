package com.example.rbmanufacturing.presentation.otk.defect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DefectFragmentViewModelFactory(private val urlConnection: String, private val uid: String, private val codeitem: Int): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DefectFragmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DefectFragmentViewModel(urlConnection, uid, codeitem) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }

}