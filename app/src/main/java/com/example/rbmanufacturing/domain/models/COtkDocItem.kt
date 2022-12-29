package com.example.rbmanufacturing.domain.models

data class COtkDocItem(
    val uid: String = "", //уникальный идентификатор номенклатуры
    val name: String = "",          //наименование
    val parameter: String = "",     //характеристика
    val count: Double = 0.0,        //необходимое количество
    var countdefectust: Double = 0.0,    //количество дефектов устранимых
    var countdefectnoust: Double = 0.0,    //количество дефектов не устранимых
    val codestr: Int = 0            //Код строки
)
