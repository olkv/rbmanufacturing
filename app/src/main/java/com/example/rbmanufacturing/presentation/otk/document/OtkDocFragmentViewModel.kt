package com.example.rbmanufacturing.presentation.otk.document

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class OtkDocFragmentViewModel(_typeDoc: String, _uid: String, _urlConnection: String): ViewModel() {

    //признак загрузки документа
    private val isLoadingState = MutableStateFlow<Boolean>(value = false)
    var isLoading: MutableStateFlow<Boolean> = isLoadingState

    //состояние выполнения операции
    private val requestResultState = MutableStateFlow<String>(value = "")
    var requestResult: MutableStateFlow<String> = requestResultState

    //тип документа
    private var typeDoc: String = ""

    //ссылка на UID документа
    private var uid: String = ""

    //строка подключения к сервису
    private var urlConnection: String = ""

    init {
        this.typeDoc = _typeDoc
        this.uid = _uid
        this.urlConnection = _urlConnection

    }

}