package com.example.rbmanufacturing.presentation.docmaster

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rbmanufacturing.domain.models.CItemWarehouse
import com.example.rbmanufacturing.domain.models.CResult
import com.example.rbmanufacturing.network.Common
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DocMasterViewModel: ViewModel() {

    //признак загрузки документа
    private val isLoadingState = MutableStateFlow<Boolean>(value = false)
    var isLoading: MutableStateFlow<Boolean> = isLoadingState

    //признак закрытия отчета мастера, при true закрывается фрагмент документа и делается переход
    //на списаок документов
    private val isCloseDocMasterState = MutableStateFlow<Boolean>(value = false)
    var isCloseDocMaster: MutableStateFlow<Boolean> = isCloseDocMasterState

    //доступность команды закрытия отчета мастера
    private val enableCloseDocMasterState = MutableStateFlow<Boolean>(value = true)
    var enableCloseDocMaster: MutableStateFlow<Boolean> = enableCloseDocMasterState

    //содержание отчета мастера
    private val docMasterState = MutableStateFlow(mutableListOf<CItemWarehouse>())
    var docMaster: MutableStateFlow<MutableList<CItemWarehouse>> = docMasterState

    //ссылка на UID документа
    private var uid: String = ""

    //строка подключения к сервису
    private var urlConnection: String = ""

    //внутренняя функция чтения данных документа из http сервиса через Retrofit2 бибилиотеку
    private fun getDocMasterRepositoryImp() {

        //установка статуса загрузки, отображается индикатор ProgressBar
        isLoadingState.value = true

        val mService = Common(urlConnection).retrofitService
        mService.getDocMaster(uid = uid).enqueue(object :
            Callback<MutableList<CItemWarehouse>> {

            override fun onFailure(call: Call<MutableList<CItemWarehouse>>, t: Throwable) {
                //ошибка выполнения запроса, убираем ProgressBar выводим в лог ошибку
                isLoadingState.value = false
                Log.d("MYLOG","Ошибка получения ${t.message}")
            }

            override fun onResponse(call: Call<MutableList<CItemWarehouse>>, response: Response<MutableList<CItemWarehouse>>) {
                //успешное выполнение запроса, получаем список из тела запроса, убираем ProgressBar, результаты заносим в переменную StateFlow
                val body = response.body() as MutableList<CItemWarehouse>
                docMasterState.value = body
                isLoadingState.value = false
            }
        })

    }


    fun updateDocMaster(docMaster: MutableList<CItemWarehouse>) {

        isLoadingState.value = true

        val gson = GsonBuilder()
            .setPrettyPrinting()
            .create()

        val jsonStr = gson.toJson(docMaster)
        Log.d("MYLOG", "JSON - $jsonStr")

        val mService = Common(urlConnection).retrofitService
        mService.updateDocMaster(strJson = jsonStr, uid = uid).enqueue(object : Callback<CResult> {

            override fun onFailure(call: Call<CResult>, t: Throwable) {
                Log.d("MYLOG", "Ошибка получения ${t.message}")
                isLoadingState.value = false
            }

            override fun onResponse(call: Call<CResult>, response: Response<CResult>) {
                val body = response.body() as CResult

                isLoadingState.value = false

                Log.d("MYLOG", "Result : ${body.res}")

                if (body.res == "OK") {
                    enableCloseDocMasterState.value = true
                    getDocument()
                }

            }
        })
    }

    fun closeDocMaster() {

        isLoadingState.value = true

        val mService = Common(urlConnection).retrofitService
        mService.closeDocMaster(uid = uid).enqueue(object : Callback<CResult> {

            override fun onFailure(call: Call<CResult>, t: Throwable) {
                Log.d("MYLOG", "Ошибка получения ${t.message}")
                isLoadingState.value = false
            }

            override fun onResponse(call: Call<CResult>, response: Response<CResult>) {
                val body = response.body() as CResult

                isLoadingState.value = false

                Log.d("MYLOG", "Result : ${body.res}")

                if (body.res == "OK") {
                    isCloseDocMasterState.value = true
                } else {
                    Log.d("MYLOG", body.res)
                }

            }
        })

    }


    //ПУБЛИЧНЫЕ МЕТОДЫ

    //внешняя функция обновления (получения) даных содержимого документа
    //запускаем в отдельном потоке, устанавливаем признак загрузки StateFlow
    fun getDocument() {

        viewModelScope.launch {
            isLoadingState.value = false
            getDocMasterRepositoryImp()
        }

    }

    //функция установки строки подключения
    fun setURLConnection(urlConnectionService: String) {
        urlConnection = urlConnectionService
        Log.d("MYLOG","URL Connection = $urlConnection")
    }

    //функция установки UID документа
    fun setUIDDoc(uid: String) {
        this.uid = uid
    }

    //функция установки признака изменения строки и отключения команды закрытия отчета мастера
    fun editRow(rowid: Int) {
        enableCloseDocMasterState.value = false
    }


}