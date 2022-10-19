package com.example.rbmanufacturing.presentation.moveitemmanf

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rbmanufacturing.data.repository.ItemWarehouseRepositoryImpl
import com.example.rbmanufacturing.domain.models.CItemWarehouse
import com.example.rbmanufacturing.domain.models.CResult
import com.example.rbmanufacturing.domain.usecase.GetItemWarehouseUseCase
import com.example.rbmanufacturing.network.Common
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoveItemManfViewModel:ViewModel() {

    private val listItemWarwhouseState = MutableStateFlow(mutableListOf<CItemWarehouse>())
    var listItemWarehouse: MutableStateFlow<MutableList<CItemWarehouse>> = listItemWarwhouseState

    private val isLoadingState = MutableStateFlow<Boolean>(value = false)
    var isLoading: MutableStateFlow<Boolean> = isLoadingState

    private val itemWarehouseRepositoryImpl = ItemWarehouseRepositoryImpl()
    private val getItemWarehouseUseCase = GetItemWarehouseUseCase(itemWarehouseRepository = itemWarehouseRepositoryImpl)

    private var itemList = mutableListOf<CItemWarehouse>()

    private var urlConnection: String = ""
    private var userName: String = "oleg"

    init {

        //getAllListWarehouseManf()

    }

    fun setUserName(username: String) {
        userName = username
    }

    fun setURLConnection(urlConnectionService: String) {
        urlConnection = urlConnectionService
        Log.d("MYLOG","URL Connection = $urlConnection")
    }

    private fun getAllItemWarehouseManf() {

        isLoadingState.value = true

        val mService = Common(urlConnection).retrofitService
        mService.getListWarehouseManf(username = userName).enqueue(object : Callback<MutableList<CItemWarehouse>> {
            override fun onFailure(call: Call<MutableList<CItemWarehouse>>, t: Throwable) {
                isLoadingState.value = false
                Log.d("MYLOG","Ошибка получения")
            }

            override fun onResponse(call: Call<MutableList<CItemWarehouse>>, response: Response<MutableList<CItemWarehouse>>) {
                val body = response.body() as MutableList<CItemWarehouse>
                listItemWarwhouseState.value = body
                isLoadingState.value = false
            }
        })

    }


    fun getAllListWarehouseManf() {

        viewModelScope.launch {
            //itemList = GetItemWarehouseManf()
            isLoadingState.value = false
            getAllItemWarehouseManf()
        }

    }


    fun pushSelectItemWarehouseManf(selectItem: MutableList<CItemWarehouse>) {
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


    fun GetItemWarehouseManf(): MutableList<CItemWarehouse> {
        return getItemWarehouseUseCase.execute()
    }



}