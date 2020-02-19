package com.vivcom.usecases

import com.vivcom.data.repository.EmployedRepository

class GetAllEmployedByIsNew(private val employedRepository: EmployedRepository) {
    suspend fun invoke(isNew: Boolean) = employedRepository.getAllEmployedByIsNew(isNew)
}