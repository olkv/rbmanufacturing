package com.example.rbmanufacturing.network

import com.example.rbmanufacturing.domain.models.*
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/*
Описание интерфейсов  HTTP сервиса
 */

interface RetrofitServices {
    @GET("version")
    fun getVersionProtocol(): Call<String>

    //список номенклатуры по которому нужно создать документ поступление из производства
    @GET("getlistmanf/{username}")
    fun getListWarehouseManf(@Path("username") username: String): Call<MutableList<CItemWarehouse>>

    //Передать в 1С список, на основании которого нужно создать документы Передача из производства
    @FormUrlEncoded
    @POST("pushitemmanf/{username}")
    fun pushItemWarehouseManf(@Field("strJson") strJson: String, @Path("username") username: String): Call<CResult>

    //Получить список Отчетов мастеров смен
    @GET("getlistoperationmaster/{username}")
    fun getListOperationMaster(@Path("username") username: String): Call<MutableList<CItemOperationMaster>>

    //Получить документ отчета мастера смены
    @GET("docmaster/{uid}")
    fun getDocMaster(@Path("uid") uid: String): Call<MutableList<CItemWarehouse>>

    //Передать изменения отчета мастера смены в 1С
    @FormUrlEncoded
    @POST("updatedocmaster/{uid}")
    fun updateDocMaster(@Field("strJson") strJson: String, @Path("uid") uid: String): Call<CResult>

    //Вызвать функцию закрытия отчета мастера смены в 1С
    @GET("closedocmaster/{uid}")
    fun closeDocMaster(@Path("uid") uid: String): Call<CResult>


    //Получить список документов ОТК
    @GET("getlistotkdocuments/{username}")
    fun getListOtkDocuments(@Path("username") username: String): Call<MutableList<COtkDocument>>

    //Получить содержимое документа ОТК
    @GET("getitemdocotk/{typedoc}/{uid}")
    fun getItemDocOtk(@Path("typedoc") typedoc: String, @Path("uid") uid: String ): Call<MutableList<COtkDocItem>>

    //Получить список дефектов
    @GET("getlistdefectotk/{uid}/{codeitem}")
    fun getListDefectOtk(@Path("uid") uid: String, @Path("codeitem") codeitem: Int ): Call<MutableList<COtkItems>>

}

