package com.example.rbmanufacturing.domain.usecase

import com.example.rbmanufacturing.domain.models.CItemWarehouse
import com.example.rbmanufacturing.domain.repository.ItemWarehouseRepository

class GetItemWarehouseUseCase(private val itemWarehouseRepository: ItemWarehouseRepository) {

    fun execute():MutableList<CItemWarehouse> {
        return itemWarehouseRepository.getItemsWarehouse()
    }

}