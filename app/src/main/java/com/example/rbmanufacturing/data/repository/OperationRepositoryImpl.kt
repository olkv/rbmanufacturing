package com.example.rbmanufacturing.data.repository

import com.example.rbmanufacturing.R
import com.example.rbmanufacturing.domain.models.CFunOperation
import com.example.rbmanufacturing.domain.repository.OperationRepository

class OperationRepositoryImpl: OperationRepository {

    override fun getFunOperation():ArrayList<CFunOperation> {
        val listFunOperation = mutableListOf<CFunOperation>()

        listFunOperation.add(CFunOperation(
            name = "Отчет мастера смены",
            code = "00001",
            description = "Подтверждение выпуска продукции по заданиям(этапам) производства",
            imageId = R.drawable.ic_baseline_content_paste_24))

        listFunOperation.add(CFunOperation(
            name = "Передача продукции из производства",
            code = "00002",
            description = "Перемещает произведеную продукцию с производства на склад хранения",
            imageId = R.drawable.ic_baseline_output_24))

        listFunOperation.add(CFunOperation(
            name = "Проверка качества продукции",
            code = "00003",
            description = "Проверка качества продукции перед приемкой на склад, регистрация причин отказа в приеме.",
            R.drawable.ic_baseline_check_circle_outline_24))

        /*
        listFunOperation.add(CFunOperation(
            name = "Передача продукции в кладовую",
            code = "00004",
            description = "Перемещает произведенную продукцию в цех производства для дальнейшей переработки"))

        listFunOperation.add(CFunOperation(
            name = "Раскрой полуфабрикатов плазма",
            code = "00005",
            description = "Создание документа производство без заказа на основании карты раскроя плазменной резки"))

         */

        return listFunOperation as ArrayList<CFunOperation>
    }

}

