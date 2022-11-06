package com.example.rbmanufacturing.domain.repository

//Интерфейс описания CallBack функции выбора (щелчка) на строке
interface RowClickListiner {
    fun OnClick(rowid: Int)
}