package com.example.rbmanufacturing.domain.models


//Класс хранения данных шапки Отчета мастера смены
data class CItemOperationMaster(
    val uid: String = "", //уникальный идентификатор документа
    val number: String = "", //номер
    val date: String = "", //дата в виде строки
    val department: String = "", //подразделение
    val description: String = "", //описание
    var isAddTask: Boolean = false, //Признак доп.задания
    var isOTKCheck: Boolean = false //Проверено службой ОТК 1*
)
