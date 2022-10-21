package com.example.rbmanufacturing.presentation.opmaster

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class OperationMasterViewModelFactory(private val userName: String, private val urlConnection: String): ViewModelProvider.Factory  {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(OperationMasterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OperationMasterViewModel(userName, urlConnection) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }


}