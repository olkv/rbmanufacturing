package com.example.rbmanufacturing.presentation.opmaster

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rbmanufacturing.domain.models.CItemOperationMaster
import com.example.rbmanufacturing.domain.models.CItemWarehouse
import com.example.rbmanufacturing.network.Common
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OperationMasterViewModel(_userName: String, _urlConnnection: String):ViewModel() {

    private val isLoadingState = MutableStateFlow<Boolean>(value = false)
    var isLoading: MutableStateFlow<Boolean> = isLoadingState

    private val listItemOperationMaster = MutableStateFlow(mutableListOf<CItemOperationMaster>())
    var listItemOperation: MutableStateFlow<MutableList<CItemOperationMaster>> = listItemOperationMaster

    //состояние выполнения операции
    private val requestResultState = MutableStateFlow<String>(value = "")
    var requestResult: MutableStateFlow<String> = requestResultState

    private var urlConnection: String = ""
    private var userName: String = "oleg"

    init {

        this.urlConnection = _urlConnnection
        this.userName = _userName

        getAllListOperationMaster()
    }



    private fun getAllListOperationMasterRepositoryImp() {

        isLoadingState.value = true

        val mService = Common(urlConnection).retrofitService
        mService.getListOperationMaster(username = userName).enqueue(object :
            Callback<MutableList<CItemOperationMaster>> {
            override fun onFailure(call: Call<MutableList<CItemOperationMaster>>, t: Throwable) {
                isLoadingState.value = false
                requestResultState.value = "Ошибка получения ${t.message}"
                Log.d("MYLOG","Ошибка получения : ${t.message}")
            }

            override fun onResponse(call: Call<MutableList<CItemOperationMaster>>, response: Response<MutableList<CItemOperationMaster>>) {
                val body = response.body() as MutableList<CItemOperationMaster>
                listItemOperationMaster.value = body
                isLoadingState.value = false
            }
        })

    }


    fun getAllListOperationMaster() {

        viewModelScope.launch {
            //itemList = GetItemWarehouseManf()
            isLoadingState.value = false
            getAllListOperationMasterRepositoryImp()
        }

    }


}