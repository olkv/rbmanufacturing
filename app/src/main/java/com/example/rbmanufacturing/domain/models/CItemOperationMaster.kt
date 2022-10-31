package com.example.rbmanufacturing.domain.models

import java.util.Date

data class CItemOperationMaster(
    var uid: String = "", //уникальный идентификатор документа
    var number: String = "", //номер
    var date: String = "", //дата в виде строки
    var department: String = "", //подразделение
    var description: String = "", //описание
    var isAddTask: Boolean = false, //доступен для закрытия
    var isOTKCheck: Boolean = false //Проверено службой ОТК
)
