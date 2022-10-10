package com.example.rbmanufacturing.domain.models

import java.util.Date

data class CItemOperationMaster(
    var uid: String = "",
    var number: String = "",
    var date: String = "",
    var department: String = "",
    var description: String = "",
    var isAddTask: Boolean = false
)
