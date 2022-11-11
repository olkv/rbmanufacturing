package com.example.rbmanufacturing.presentation.otk.documents

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rbmanufacturing.domain.models.CItemOperationMaster
import com.example.rbmanufacturing.domain.models.COtkDocument
import com.example.rbmanufacturing.network.Common
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtkDocumentsViewModel(_userName: String, _urlConnnection: String): ViewModel() {

    private var urlConnection: String = ""
    private var userName: String = "oleg"

    private val isLoadingState = MutableStateFlow<Boolean>(value = false)
    var isLoading: MutableStateFlow<Boolean> = isLoadingState

    //состояние выполнения операции
    private val requestResultState = MutableStateFlow<String>(value = "")
    var requestResult: MutableStateFlow<String> = requestResultState

    private val listOtkDocuments = MutableStateFlow(mutableListOf<COtkDocument>())
    var listItem: MutableStateFlow<MutableList<COtkDocument>> = listOtkDocuments


    init {

        this.urlConnection = _urlConnnection
        this.userName = _userName

        getListOtkDocuments()

    }

    private fun getListOtkDocumentsRepositoryImp() {

        isLoadingState.value = true

        val mService = Common(urlConnection).retrofitService
        mService.getListOtkDocuments(username = userName).enqueue(object :
            Callback<MutableList<COtkDocument>> {
            override fun onFailure(call: Call<MutableList<COtkDocument>>, t: Throwable) {
                isLoadingState.value = false
                requestResultState.value = "Ошибка получения ${t.message}"
                Log.d("MYLOG","Ошибка получения : ${t.message}")
            }

            override fun onResponse(call: Call<MutableList<COtkDocument>>, response: Response<MutableList<COtkDocument>>) {
                val body = response.body() as MutableList<COtkDocument>
                listOtkDocuments.value = body
                isLoadingState.value = false
            }
        })

    }


    fun getListOtkDocuments() {

        viewModelScope.launch {
            isLoadingState.value = false
            getListOtkDocumentsRepositoryImp()
        }

    }


}