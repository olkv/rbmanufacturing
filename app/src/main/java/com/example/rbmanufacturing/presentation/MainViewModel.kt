package com.example.rbmanufacturing.presentation

import androidx.lifecycle.ViewModel
import com.example.rbmanufacturing.data.repository.OperationRepositoryImpl
import com.example.rbmanufacturing.domain.models.CFunOperation
import com.example.rbmanufacturing.domain.usecase.GetListFunOperationUseCase

class MainViewModel: ViewModel() {

    private val operationRepositoryImpl = OperationRepositoryImpl()
    private val getListFunOperationUseCase = GetListFunOperationUseCase(operationRepository = operationRepositoryImpl)

    fun GetListOperation():ArrayList<CFunOperation> {
        return getListFunOperationUseCase.execute()
    }

}