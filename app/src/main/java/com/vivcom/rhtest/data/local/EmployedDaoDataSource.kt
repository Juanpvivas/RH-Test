package com.vivcom.rhtest.data.local

import com.vivcom.data.source.LocalDataSource
import com.vivcom.domain.Employed
import com.vivcom.rhtest.data.mappers.toDomainEmployed
import com.vivcom.rhtest.data.mappers.toRoomEmployed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EmployedDaoDataSource(private val employedDao: EmployedDao) : LocalDataSource {

    override suspend fun getAllEmployees(): List<Employed> =
        withContext(Dispatchers.IO) { employedDao.getAll().map { it.toDomainEmployed() } }


    override suspend fun isEmpty(): Boolean =
        withContext(Dispatchers.IO) { employedDao.employedCount() <= 0 }

    override suspend fun saveEmployees(employees: List<Employed>) {
        withContext(Dispatchers.IO) {
            employedDao.insertEmployees(employees.map { it.toRoomEmployed() })
        }
    }

    override suspend fun findById(id: Int): Employed = withContext(Dispatchers.IO) {
        employedDao.findById(id).toDomainEmployed()
    }

    override suspend fun update(employed: Employed) {
        withContext(Dispatchers.IO) { employedDao.updateEmployed(employed.toRoomEmployed()) }
    }
}