package com.vivcom.usecases

import com.vivcom.data.repository.EmployedRepository
import com.vivcom.domain.Employed

class ToggleNewEmployed(private val repository: EmployedRepository) {
    suspend fun invoke(employed: Employed) {
        repository.update(employed)
    }
}