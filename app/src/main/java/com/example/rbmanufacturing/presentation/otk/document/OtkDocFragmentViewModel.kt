package com.example.rbmanufacturing.presentation.otk.document

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rbmanufacturing.domain.models.CItemWarehouse
import com.example.rbmanufacturing.domain.models.COtkDocItem
import com.example.rbmanufacturing.network.Common
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtkDocFragmentViewModel(_typeDoc: String, _uid: String, _urlConnection: String): ViewModel() {

    //признак загрузки документа
    private val isLoadingState = MutableStateFlow<Boolean>(value = false)
    var isLoading: MutableStateFlow<Boolean> = isLoadingState

    //состояние выполнения операции
    private val requestResultState = MutableStateFlow<String>(value = "")
    var requestResult: MutableStateFlow<String> = requestResultState

    //содержание document
    private val docOtkState = MutableStateFlow(mutableListOf<COtkDocItem>())
    var docOtk: MutableStateFlow<MutableList<COtkDocItem>> = docOtkState

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

        getDocument()
    }


    private fun getDocOtkRepositoryImp() {

        //установка статуса загрузки, отображается индикатор ProgressBar
        isLoadingState.value = true

        val mService = Common(urlConnection).retrofitService
        mService.getItemDocOtk(typedoc = typeDoc, uid = uid).enqueue(object :
            Callback<MutableList<COtkDocItem>> {

            override fun onFailure(call: Call<MutableList<COtkDocItem>>, t: Throwable) {
                //ошибка выполнения запроса, убираем ProgressBar выводим в лог ошибку
                isLoadingState.value = false
                requestResultState.value = "Ошибка получения ${t.message}"
                Log.d("MYLOG","Ошибка получения ${t.message}")
            }

            override fun onResponse(call: Call<MutableList<COtkDocItem>>, response: Response<MutableList<COtkDocItem>>) {
                //успешное выполнение запроса, получаем список из тела запроса, убираем ProgressBar, результаты заносим в переменную StateFlow
                val body = response.body() as MutableList<COtkDocItem>
                docOtkState.value = body
                isLoadingState.value = false
            }
        })

    }


    fun getDocument() {

        viewModelScope.launch {
            isLoadingState.value = false
            getDocOtkRepositoryImp()
        }

    }


}