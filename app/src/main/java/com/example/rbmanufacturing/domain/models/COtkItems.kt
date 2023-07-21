package com.example.rbmanufacturing.domain.models

//Строка выявленного дефекта
data class COtkItems(
    val uid_defect: String = "",
    var uid_doc: String = "",
    var codeitem: Int = 0,
    var vid_defect: String = "",
    var type_defect: String = "",
    var count: Int = 0,
    var description: String = "",
    var strImage: MutableList<String> = mutableListOf()
)