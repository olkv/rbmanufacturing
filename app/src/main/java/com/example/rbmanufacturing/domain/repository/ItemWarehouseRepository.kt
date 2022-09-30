package com.example.rbmanufacturing.domain.repository

import com.example.rbmanufacturing.domain.models.CItemWarehouse

interface ItemWarehouseRepository {
    fun getItemsWarehouse():MutableList<CItemWarehouse>
}