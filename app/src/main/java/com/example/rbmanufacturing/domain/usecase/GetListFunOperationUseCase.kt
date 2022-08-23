package com.example.rbmanufacturing.domain.usecase

import com.example.rbmanufacturing.domain.models.CFunOperation
import com.example.rbmanufacturing.domain.repository.OperationRepository

class GetListFunOperationUseCase(private val operationRepository: OperationRepository) {

    fun execute(): ArrayList<CFunOperation> {
        return operationRepository.getFunOperation()
    }
}