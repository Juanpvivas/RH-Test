package com.vivcom.data.source

import com.vivcom.domain.Employed


interface LocalDataSource {
    suspend fun saveEmployees(employees: List<Employed>)
    suspend fun findById(id: Int): Employed
    suspend fun update(employed: Employed)
    suspend fun isEmpty(): Boolean
    suspend fun getAllEmployees(): List<Employed>
    suspend fun findSubordinatesById(id: Int): List<Employed>
    suspend fun getAllEmployeesByIsNew(isNew: Boolean): List<Employed>
}