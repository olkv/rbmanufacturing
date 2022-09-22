package com.example.rbmanufacturing.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rbmanufacturing.data.repository.OperationRepositoryImpl
import com.example.rbmanufacturing.domain.models.CFunOperation
import com.example.rbmanufacturing.domain.usecase.GetListFunOperationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val listFunOperationState = MutableStateFlow(mutableListOf<CFunOperation>())
    var listFunOperation: MutableStateFlow<MutableList<CFunOperation>> = listFunOperationState

    private val operationRepositoryImpl = OperationRepositoryImpl()
    private val getListFunOperationUseCase = GetListFunOperationUseCase(operationRepository = operationRepositoryImpl)

    private var operationList = mutableListOf<CFunOperation>()

    init {

        viewModelScope.launch {
            operationList = GetListOperation()
            listFunOperationState.value = operationList
        }

    }

    fun GetListOperation(): MutableList<CFunOperation> {
        return getListFunOperationUseCase.execute()
    }


}