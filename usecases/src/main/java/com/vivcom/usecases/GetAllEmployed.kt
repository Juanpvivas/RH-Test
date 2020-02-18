package com.vivcom.usecases

import com.vivcom.data.repository.EmployedRepository
import com.vivcom.data.repository.ResultData
import com.vivcom.domain.Employed

class GetAllEmployed(private val employedRepository: EmployedRepository) {
    suspend fun invoke(): ResultData<List<Employed>> = employedRepository.getAllEmployed()
}