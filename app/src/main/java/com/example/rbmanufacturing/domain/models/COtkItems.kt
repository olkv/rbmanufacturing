package com.example.rbmanufacturing.domain.models

//Строка выявленного дефекта
data class COtkItems(
    val uid_defect: String,
    val uid_doc: String,
    val codeitem: Int,
    val vid_defect: String,
    val type_defect: String,
    val count: Int = 0,
    val description: String = "",
    val strImage: MutableList<String> = mutableListOf()
)