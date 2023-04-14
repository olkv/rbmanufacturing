package com.example.rbmanufacturing.presentation.otk.defect

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class EditDefectViewModel: ViewModel() {

    //состояние выполнения операции
    private val requestResultState = MutableStateFlow<String>(value = "")
    var requestResult: MutableStateFlow<String> = requestResultState

    fun setParameters(par:Bundle?) {
        requestResultState.value = "OK"

        val description = par?.getString("description")
        Log.d("MYLOG", "description =$description")

    }

    fun clearResult() {
        requestResultState.value = ""
    }

}