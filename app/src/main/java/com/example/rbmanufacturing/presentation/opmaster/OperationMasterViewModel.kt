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

class OperationMasterViewModel:ViewModel() {

    private val isLoadingState = MutableStateFlow<Boolean>(value = false)
    var isLoading: MutableStateFlow<Boolean> = isLoadingState

    private val listItemOperationMaster = MutableStateFlow(mutableListOf<CItemOperationMaster>())
    var listItemOperation: MutableStateFlow<MutableList<CItemOperationMaster>> = listItemOperationMaster

    private var urlConnection: String = ""

    init {

       //getAllListOperationMaster()
    }


    fun setURLConnection(urlConnectionService: String) {
        urlConnection = urlConnectionService
        Log.d("MYLOG","URL Connection = $urlConnection")
    }

    private fun getAllItemOperationMaster(username: String) {

        isLoadingState.value = true

        val mService = Common.retrofitService
        mService.getListOperationMaster(username = username).enqueue(object :
            Callback<MutableList<CItemOperationMaster>> {
            override fun onFailure(call: Call<MutableList<CItemOperationMaster>>, t: Throwable) {
                isLoadingState.value = false
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
            getAllItemOperationMaster("oleg")
        }

    }


}