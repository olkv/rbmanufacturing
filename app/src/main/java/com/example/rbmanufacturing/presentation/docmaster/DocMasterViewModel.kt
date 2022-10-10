package com.example.rbmanufacturing.presentation.docmaster

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class DocMasterViewModel: ViewModel() {

    private val isLoadingState = MutableStateFlow<Boolean>(value = false)
    var isLoading: MutableStateFlow<Boolean> = isLoadingState

    private var uid: String = ""

    init {

    }

    fun setUIDDoc(uid: String) {
        this.uid = uid
    }

}