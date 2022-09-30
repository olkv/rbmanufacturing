package com.example.rbmanufacturing.domain.models

class CItemWarehouse(
    val uid:String,
    val name: String,
    val parameter: String,
    val stage: String,
    val custom: String,
    val appointment: String,
    val count: Double,
    var editcount: Double
)