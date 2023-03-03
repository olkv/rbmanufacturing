package com.example.rbmanufacturing.domain.models

data class COtkDocument(
    val type_doc: String = "", //Тип документа
    val uid: String = "", //уникальный идентификатор документа
    val number: String = "", //номер
    val date: String = "", //дата в виде строки
    val department: String = "", //подразделение
    val description: String = "", //описание
    var isOTKCheck: Boolean = false, //Проверено службой ОТК
    var isOTKChecked: Boolean = false //необходимо проверить службой ОТК
)
