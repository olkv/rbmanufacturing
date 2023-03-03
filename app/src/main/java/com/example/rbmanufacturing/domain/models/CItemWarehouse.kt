package com.example.rbmanufacturing.domain.models

//Класс (строка) описывающаю таблицу движения продукции на складе
data class CItemWarehouse(
    var uid:String = "",            //уникальный код номенклатуры
    var name: String = "",          //наименование
    var parameter: String = "",     //характеристика
    var stage: String = "",         //этап производства
    var custom: String = "",        //заказ производства
    var appointment: String= "",    //назначение
    var count: Double = 0.0,        //необходимое количество
    var editcount: Double = 0.0,    //выбранное количество
    var codestr: Int = 0,           //Код строки
    var maxcount: Double = 0.0      //максимальноое редактируемое количество
)