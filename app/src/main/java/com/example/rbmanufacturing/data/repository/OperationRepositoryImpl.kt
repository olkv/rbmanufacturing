package com.example.rbmanufacturing.data.repository

import com.example.rbmanufacturing.domain.models.CFunOperation
import com.example.rbmanufacturing.domain.repository.OperationRepository

class OperationRepositoryImpl: OperationRepository {

    override fun getFunOperation():ArrayList<CFunOperation> {
        val listFunOperation = mutableListOf<CFunOperation>()

        listFunOperation.add(CFunOperation(name = "Передача материалов в производство", code = "00001", description = "Перемещает материалы со склада в производство"))
        listFunOperation.add(CFunOperation(name = "Передача продукции из производства", code = "00002", description = "Перемещает произведеную продукцию с производства на склад хранения"))
        listFunOperation.add(CFunOperation(name = "Передача продукции в кладовую", code = "00003", description = "Перемещает произведенную продукцию в цех производства для дальнейшей переработки"))
        listFunOperation.add(CFunOperation(name = "Раскрой полуфабрикатов плазма", code = "00004", description = "Создание документа производство без заказа на основании карты раскроя плазменной резки"))

        return listFunOperation as ArrayList<CFunOperation>
    }

}

