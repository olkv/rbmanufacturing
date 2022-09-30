package com.example.rbmanufacturing.data.repository

import com.example.rbmanufacturing.domain.models.CItemWarehouse
import com.example.rbmanufacturing.domain.repository.ItemWarehouseRepository

class ItemWarehouseRepositoryImpl:ItemWarehouseRepository {
    override fun getItemsWarehouse(): MutableList<CItemWarehouse> {
        var items = mutableListOf<CItemWarehouse>()

        items.add(
            CItemWarehouse(
                uid = "af1234-assdsd-23343",
                name = "Щит угловой 1060х300х2000",
                parameter = "",
                count = 10.0,
                editcount = 0.0)
        )

        items.add(
            CItemWarehouse(
                uid = "dr334-3434dfg-33fdf",
                name = "ЩЛ",
                parameter = "(2,5) 1,2х3,300 / RAL7011",
                count = 2.0,
                editcount = 0.0)
        )

        return  items
    }
}