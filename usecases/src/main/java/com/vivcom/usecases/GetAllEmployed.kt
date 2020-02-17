package com.vivcom.usecases

import com.vivcom.data.repository.EmployedRepository

class GetAllEmployed(private val employedRepository: EmployedRepository) {
    suspend fun invoke() {
        employedRepository.getAllEmployed()
    }
}