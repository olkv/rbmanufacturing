package com.example.rbmanufacturing.presentation.moveitemmanf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rbmanufacturing.data.repository.ItemWarehouseRepositoryImpl
import com.example.rbmanufacturing.domain.models.CItemWarehouse
import com.example.rbmanufacturing.domain.usecase.GetItemWarehouseUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MoveItemManfViewModel:ViewModel() {

    private val listItemWarwhouseState = MutableStateFlow(mutableListOf<CItemWarehouse>())
    var listItemWarehouse: MutableStateFlow<MutableList<CItemWarehouse>> = listItemWarwhouseState

    private val itemWarehouseRepositoryImpl = ItemWarehouseRepositoryImpl()
    private val getItemWarehouseUseCase = GetItemWarehouseUseCase(itemWarehouseRepository = itemWarehouseRepositoryImpl)

    private var itemList = mutableListOf<CItemWarehouse>()

    init {

        viewModelScope.launch {
            itemList = GetItemWarehouseManf()
            listItemWarwhouseState.value = itemList
        }

    }

    fun GetItemWarehouseManf(): MutableList<CItemWarehouse> {
        return getItemWarehouseUseCase.execute()
    }

}