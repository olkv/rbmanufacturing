package com.example.rbmanufacturing.domain.models


//Класс хранения данных шапки Отчета мастера смены
data class CItemOperationMaster(
    var uid: String = "", //уникальный идентификатор документа
    var number: String = "", //номер
    var date: String = "", //дата в виде строки
    var department: String = "", //подразделение
    var description: String = "", //описание
    var isAddTask: Boolean = false, //Признак доп.задания
    var isOTKCheck: Boolean = false //Проверено службой ОТК
)
