package com.vivcom.rhtest.data.local

import androidx.room.*

@Dao
interface EmployedDao {
    @Query("SELECT * FROM Employed")
    fun getAll(): List<Employed>

    @Query("SELECT * FROM Employed WHERE upperRelation = :id")
    fun getAllSubordinates(id: Int): List<Employed>

    @Query("SELECT * FROM Employed WHERE id = :id")
    fun findById(id: Int): Employed

    @Query("SELECT COUNT(id) FROM Employed")
    fun employedCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertEmployees(employees: List<Employed>)

    @Update
    fun updateEmployed(employed: Employed)

    @Query("SELECT * FROM Employed WHERE isNew = :isNew")
    fun getAllEmployeesByIsNew(isNew: Boolean): List<Employed>
}