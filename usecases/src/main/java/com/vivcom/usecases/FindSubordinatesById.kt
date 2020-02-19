package com.vivcom.usecases

import com.vivcom.data.repository.EmployedRepository

class FindSubordinatesById(private val employedRepository: EmployedRepository) {
    suspend fun invoke(id: Int) = employedRepository.findSubordinatesById(id)
}