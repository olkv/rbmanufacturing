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

    private val isLoadingState = MutableStateFlow<Boolean>(value = false)
    var isLoading: MutableStateFlow<Boolean> = isLoadingState

    private val isCloseDocMasterState = MutableStateFlow<Boolean>(value = false)
    var isCloseDocMaster: MutableStateFlow<Boolean> = isCloseDocMasterState

    private val docMasterState = MutableStateFlow(mutableListOf<CItemWarehouse>())
    var docMaster: MutableStateFlow<MutableList<CItemWarehouse>> = docMasterState

    private var uid: String = ""

    init {

    }

    fun setUIDDoc(uid: String) {
        this.uid = uid
    }

    private fun getDocMaster(uid: String) {

        isLoadingState.value = true

        val mService = Common.retrofitService
        mService.getDocMaster(uid = uid).enqueue(object :
            Callback<MutableList<CItemWarehouse>> {
            override fun onFailure(call: Call<MutableList<CItemWarehouse>>, t: Throwable) {
                isLoadingState.value = false
                Log.d("MYLOG","Ошибка получения")
            }

            override fun onResponse(call: Call<MutableList<CItemWarehouse>>, response: Response<MutableList<CItemWarehouse>>) {
                val body = response.body() as MutableList<CItemWarehouse>
                docMasterState.value = body
                isLoadingState.value = false
            }
        })

    }

    fun getDocument() {

        viewModelScope.launch {
            isLoadingState.value = false
            getDocMaster(uid = uid)
        }

    }

    fun updateDocMaster(docMaster: MutableList<CItemWarehouse>) {

        isLoadingState.value = true

        val gson = GsonBuilder()
            .setPrettyPrinting()
            .create()

        val jsonStr = gson.toJson(docMaster)
        Log.d("MYLOG", "JSON - $jsonStr")

        val mService = Common.retrofitService
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
                    getDocument()
                }

            }
        })
    }

    fun closeDocMaster() {

        isLoadingState.value = true

        val mService = Common.retrofitService
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
                }

            }
        })

    }


}