package com.vivcom.usecases

import com.vivcom.data.repository.EmployedRepository
import com.vivcom.domain.Employed

class ToggleNewEmployed(private val employedRepository: EmployedRepository) {
    suspend fun invoke(employed: Employed): Employed = with(employed) {
        copy(isNew = !isNew).also { employedRepository.update(it) }
    }
}