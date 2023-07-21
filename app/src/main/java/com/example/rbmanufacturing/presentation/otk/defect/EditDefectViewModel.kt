package com.example.rbmanufacturing.presentation.otk.defect

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.rbmanufacturing.domain.models.COtkItems
import com.example.rbmanufacturing.domain.models.CResult
import com.example.rbmanufacturing.network.Common
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditDefectViewModel: ViewModel() {

    //состояние выполнения операции
    private val requestResultState = MutableStateFlow<String>(value = "")
    var requestResult: MutableStateFlow<String> = requestResultState

    fun setParameters(par:Bundle?) {

        var itemDefect = COtkItems()

        if (par != null) {

            val urlConn = par.getString("urlconnection").toString()

            itemDefect.count = par.getInt("count")
            itemDefect.description = par.getString("description").toString()
            itemDefect.vid_defect = par.getString("viddefect").toString()
            itemDefect.type_defect = par.getString("typedefect").toString()
            itemDefect.uid_doc = par.getString("uid_doc").toString()
            itemDefect.codeitem = par.getInt("codeitem")

            //Необходимо вызвать сервис добавления дефекта
            AddDefectImp(urlConn, itemDefect)

        }
        else {
            Log.d("MYLOG", "Не задана структура параметров")
        }

    }


    private fun AddDefectImp(urlConnection:String, item:COtkItems) {

        val gson = GsonBuilder()
            .setPrettyPrinting()
            .create()

        val jsonStr = gson.toJson(item)
        Log.d("MYLOG", "JSON - $jsonStr")

        val mService = Common(urlConnection).retrofitService

        mService.AddDefect(strJson = jsonStr).enqueue(object : Callback<CResult> {

            override fun onResponse(call: Call<CResult>, response: Response<CResult>) {

                val body = response.body() as CResult

                Log.d("MYLOG", "Result : ${body.res}")

                if (body.res == "OK") {
                    requestResultState.value = "OK"
                }
            }

            override fun onFailure(call: Call<CResult>, t: Throwable) {

                requestResultState.value = "Ошибка получения ${t.message}"
                Log.d("MYLOG", "Ошибка получения ${t.message}")

            }

        })

    }


    fun clearResult() {
        requestResultState.value = ""
    }

}