package com.example.rbmanufacturing.domain.repository

interface RowChangeListiner {
    fun onChange(rowid : Int)
}