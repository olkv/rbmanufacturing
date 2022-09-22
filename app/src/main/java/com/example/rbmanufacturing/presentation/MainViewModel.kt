package com.example.rbmanufacturing.presentation

import androidx.lifecycle.ViewModel
import com.example.rbmanufacturing.data.repository.OperationRepositoryImpl
import com.example.rbmanufacturing.domain.models.CFunOperation
import com.example.rbmanufacturing.domain.usecase.GetListFunOperationUseCase
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel: ViewModel() {

    private val listFunOperationState = MutableStateFlow(mutableListOf<CFunOperation>())
    var listFunOperation: MutableStateFlow<MutableList<CFunOperation>> = listFunOperationState

    private val operationRepositoryImpl = OperationRepositoryImpl()
    private val getListFunOperationUseCase = GetListFunOperationUseCase(operationRepository = operationRepositoryImpl)

    /*
    init {


        viewModelScope.launch {
            CountState.collect {

            }
        }


    }
    */

    fun GetListOperation(): MutableList<CFunOperation> {
        return getListFunOperationUseCase.execute()
    }

    fun getListOperationFlow() {

    }

}