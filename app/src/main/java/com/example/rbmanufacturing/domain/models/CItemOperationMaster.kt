package com.example.rbmanufacturing.domain.models

import java.util.Date

data class CItemOperationMaster(
    var uid: String = "",
    var number: String = "",
    var date: Date = Date(),
    var dapartment: String = "",
    var description: String = "",
    var isAddTask: Boolean = false
)
