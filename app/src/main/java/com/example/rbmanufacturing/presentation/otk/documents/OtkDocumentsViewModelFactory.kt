package com.example.rbmanufacturing.presentation.otk.documents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rbmanufacturing.presentation.opmaster.OperationMasterViewModel

class OtkDocumentsViewModelFactory(private val userName: String, private val urlConnection: String): ViewModelProvider.Factory  {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(OtkDocumentsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OtkDocumentsViewModel(userName, urlConnection) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}