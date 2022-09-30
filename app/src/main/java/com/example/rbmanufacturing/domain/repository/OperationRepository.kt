package com.example.rbmanufacturing.domain.repository

import com.example.rbmanufacturing.domain.models.CFunOperation

interface OperationRepository {
    fun getFunOperation():ArrayList<CFunOperation>
}