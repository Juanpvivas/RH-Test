package com.vivcom.usecases

import com.vivcom.data.repository.EmployedRepository
import com.vivcom.testShared.mockEmployed
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito

class FindSubordinatesByIdTest {

    private val employedRepository = Mockito.mock(EmployedRepository::class.java)
    private lateinit var findSubordinatesById: FindSubordinatesById

    @Before
    fun setUp() {
        findSubordinatesById = FindSubordinatesById(employedRepository)
    }

    @After
    fun afterTest() {
        Mockito.verifyNoMoreInteractions(employedRepository)
    }

    @Test
    operator fun invoke() {
        val list = listOf(mockEmployed, mockEmployed)
        runBlocking {
            Mockito.`when`(employedRepository.findSubordinatesById(1)).thenReturn(list)

            val result = findSubordinatesById.invoke(1)

            Mockito.verify(employedRepository).findSubordinatesById(1)
            assertEquals(list, result)
        }
    }
}