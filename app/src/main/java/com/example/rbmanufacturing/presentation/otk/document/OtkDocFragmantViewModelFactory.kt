package com.example.rbmanufacturing.presentation.otk.document

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class OtkDocFragmantViewModelFactory(private val typedoc: String, private val uid: String, private val urlConnection: String): ViewModelProvider.Factory  {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(OtkDocFragmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OtkDocFragmentViewModel(typedoc, uid, urlConnection) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")

    }
}