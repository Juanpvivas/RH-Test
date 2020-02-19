package com.vivcom.usecases

import com.vivcom.data.repository.EmployedRepository
import com.vivcom.domain.Employed

class FindEmployedById(private val employedRepository: EmployedRepository) {

    suspend fun invoke(employedId: Int): Employed = employedRepository.findEmployedById(employedId)
}