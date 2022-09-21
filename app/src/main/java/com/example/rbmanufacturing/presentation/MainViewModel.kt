package com.example.rbmanufacturing.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rbmanufacturing.data.repository.OperationRepositoryImpl
import com.example.rbmanufacturing.domain.models.CFunOperation
import com.example.rbmanufacturing.domain.usecase.GetListFunOperationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val mCountState = MutableStateFlow(0)
    val CountState: StateFlow<Int> = mCountState

    private val operationRepositoryImpl = OperationRepositoryImpl()
    private val getListFunOperationUseCase = GetListFunOperationUseCase(operationRepository = operationRepositoryImpl)

    init {

        /*
        viewModelScope.launch {
            CountState.collect {

            }
        }
        */

        TODO()
    }


    fun GetListOperation():ArrayList<CFunOperation> {
        val res = getListFunOperationUseCase.execute()

        if (res.size>0) {

        }

        return res
    }

}