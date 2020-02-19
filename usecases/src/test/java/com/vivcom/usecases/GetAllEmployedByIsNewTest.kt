package com.vivcom.usecases

import com.vivcom.data.repository.EmployedRepository
import com.vivcom.testShared.mockEmployed
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class GetAllEmployedByIsNewTest {

    private val employedRepository = mock(EmployedRepository::class.java)
    private lateinit var getAllEmployedByIsNew: GetAllEmployedByIsNew

    @After
    fun afterTest(){
        verifyNoMoreInteractions(employedRepository)
    }

    @Before
    fun setUp() {
        getAllEmployedByIsNew = GetAllEmployedByIsNew(employedRepository)
    }

    @Test
    fun invoke() {
        val list = listOf(mockEmployed, mockEmployed)
        runBlocking {
            `when`(employedRepository.getAllEmployedByIsNew(true)).thenReturn(list)

            val result = getAllEmployedByIsNew.invoke(true)

            verify(employedRepository).getAllEmployedByIsNew(true)
            assertEquals(list, result)
        }
    }
}