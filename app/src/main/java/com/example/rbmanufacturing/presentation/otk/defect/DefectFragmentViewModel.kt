package com.example.rbmanufacturing.presentation.otk.defect

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rbmanufacturing.domain.models.COtkDocItem
import com.example.rbmanufacturing.domain.models.COtkItems
import com.example.rbmanufacturing.network.Common
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DefectFragmentViewModel(_urlConnection: String, _uid: String, _codeitem: Int): ViewModel() {

    //строка подключения к сервису
    private var urlConnection: String = ""
    private var uid: String = ""
    private var codeitem: Int = 0

    //признак загрузки документа
    private val isLoadingState = MutableStateFlow<Boolean>(value = false)
    var isLoading: MutableStateFlow<Boolean> = isLoadingState

    //состояние выполнения операции
    private val requestResultState = MutableStateFlow<String>(value = "")
    var requestResult: MutableStateFlow<String> = requestResultState

    //содержание document
    private val itemsOtkState = MutableStateFlow(mutableListOf<COtkItems>())
    var itemsOtk: MutableStateFlow<MutableList<COtkItems>> = itemsOtkState

    init {

        this.urlConnection = _urlConnection
        this.uid = _uid
        this.codeitem = _codeitem

        getListDefectOTK()

    }

    private fun getListDefectOtkRepositoryImp() {

        //установка статуса загрузки, отображается индикатор ProgressBar
        isLoadingState.value = true

        val mService = Common(urlConnection).retrofitService
        mService.getListDefectOtk(uid = uid, codeitem = codeitem).enqueue(object :
            Callback<MutableList<COtkItems>> {

            override fun onFailure(call: Call<MutableList<COtkItems>>, t: Throwable) {
                //ошибка выполнения запроса, убираем ProgressBar выводим в лог ошибку
                isLoadingState.value = false
                requestResultState.value = "Ошибка получения ${t.message}"
                Log.d("MYLOG","Ошибка получения ${t.message}")
            }

            override fun onResponse(call: Call<MutableList<COtkItems>>, response: Response<MutableList<COtkItems>>) {
                //успешное выполнение запроса, получаем список из тела запроса, убираем ProgressBar, результаты заносим в переменную StateFlow
                val body = response.body() as MutableList<COtkItems>
                itemsOtkState.value = body
                isLoadingState.value = false

                Log.d("MYLOG","Количество элементов ${body.size.toString()}")

            }
        })

    }


    fun getListDefectOTK() {

        viewModelScope.launch {
            isLoadingState.value = false
            getListDefectOtkRepositoryImp()
        }

    }


}