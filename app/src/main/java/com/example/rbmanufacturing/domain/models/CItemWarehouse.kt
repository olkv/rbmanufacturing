package com.example.rbmanufacturing.domain.models

//Класс (строка) описывающаю таблицу движения продукции на складе
data class CItemWarehouse(
    val uid:String = "",            //уникальный код номенклатуры
    val name: String = "",          //наименование
    val parameter: String = "",     //характеристика
    val stage: String = "",         //этап производства
    val custom: String = "",        //заказ производства
    val appointment: String= "",    //назначение
    val count: Double = 0.0,        //необходимое количество
    var editcount: Double = 0.0,    //выбранное количество
    val codestr: Int = 0,           //Код строки
    val maxcount: Double = 0.0      //максимальноое редактируемое количество
)