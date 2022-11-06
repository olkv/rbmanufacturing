package com.example.rbmanufacturing.domain.repository

//Интерфейс для вызова CallBack функции исзменения строки в списке
interface RowChangeListiner {
    fun onChange(rowid : Int)
}