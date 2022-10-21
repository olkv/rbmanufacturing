package com.example.rbmanufacturing.presentation.moveitemmanf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MoveItemManfViewModelFactory(private val userName: String, private val urlConnection: String): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MoveItemManfViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MoveItemManfViewModel(userName, urlConnection) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }

}