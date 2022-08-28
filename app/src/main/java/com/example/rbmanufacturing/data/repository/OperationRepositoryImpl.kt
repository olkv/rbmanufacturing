package com.example.rbmanufacturing.data.repository

import com.example.rbmanufacturing.domain.models.CFunOperation
import com.example.rbmanufacturing.domain.repository.OperationRepository

class OperationRepositoryImpl: OperationRepository {

    override fun getFunOperation():ArrayList<CFunOperation> {
        val listFunOperation = mutableListOf<CFunOperation>()

        listFunOperation.add(CFunOperation(name = "Передача материалов в производство", code = "00001"))

        return listFunOperation as ArrayList<CFunOperation>
    }

}