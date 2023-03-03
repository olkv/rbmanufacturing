package com.example.rbmanufacturing.presentation.moveitemmanf

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

class MoveItemManfViewModel(_userName: String, _urlConnection: String):ViewModel() {

    private val listItemWarwhouseState = MutableStateFlow(mutableListOf<CItemWarehouse>())
    var listItemWarehouse: MutableStateFlow<MutableList<CItemWarehouse>> = listItemWarwhouseState

    private val isLoadingState = MutableStateFlow<Boolean>(value = false)
    var isLoading: MutableStateFlow<Boolean> = isLoadingState

    //состояние выполнения операции
    private val requestResultState = MutableStateFlow<String>(value = "")
    var requestResult: MutableStateFlow<String> = requestResultState

    private var itemList = mutableListOf<CItemWarehouse>()

    private var urlConnection: String = ""
    private var userName: String = "oleg"

    init {

        this.userName = _userName
        this.urlConnection = _urlConnection

        getAllListWarehouseManf()

    }

    private fun getAllListWarehouseManfRepositoryImp() {

        isLoadingState.value = true

        val mService = Common(urlConnection).retrofitService
        mService.getListWarehouseManf(username = userName).enqueue(object : Callback<MutableList<CItemWarehouse>> {
            override fun onFailure(call: Call<MutableList<CItemWarehouse>>, t: Throwable) {
                isLoadingState.value = false
                requestResultState.value = "Ошибка получения ${t.message}"

                Log.d("MYLOG","Ошибка получения ${t.message}")
            }

            override fun onResponse(call: Call<MutableList<CItemWarehouse>>, response: Response<MutableList<CItemWarehouse>>) {
                val body = response.body() as MutableList<CItemWarehouse>
                listItemWarwhouseState.value = body
                isLoadingState.value = false
            }
        })

    }


    private fun pushSelectItemWarehouseManfRepositoryImp(selectItem: MutableList<CItemWarehouse>) {
        Log.d("MYLOG", "Count list ${selectItem.size} elements")

        isLoadingState.value = true

        val gson = GsonBuilder()
            .setPrettyPrinting()
            .create()

        val jsonStr = gson.toJson(selectItem)
        Log.d("MYLOG", "JSON - $jsonStr")

        val mService = Common(urlConnection).retrofitService

        mService.pushItemWarehouseManf(strJson = jsonStr, username = userName).enqueue(object : Callback<CResult> {

            override fun onFailure(call: Call<CResult>, t: Throwable) {
                Log.d("MYLOG","Ошибка получения ${t.message}")
                requestResultState.value = "Ошибка получения ${t.message}"
                isLoadingState.value = false
            }

            override fun onResponse(call: Call<CResult>, response: Response<CResult>) {

                val body = response.body() as CResult

                isLoadingState.value = false

                Log.d("MYLOG","Result : ${body.res}")

                if(body.res=="OK") {
                    getAllListWarehouseManf()
                }

            }
        })

    }


    fun getAllListWarehouseManf() {

        viewModelScope.launch {
            isLoadingState.value = false
            getAllListWarehouseManfRepositoryImp()
        }

    }

    fun pushSelectItemWarehouseManf(selectItem: MutableList<CItemWarehouse>)  {
        viewModelScope.launch {
            isLoadingState.value = false
            pushSelectItemWarehouseManfRepositoryImp(selectItem)
        }

    }




}