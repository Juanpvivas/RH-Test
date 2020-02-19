package com.vivcom.usecases

import com.vivcom.data.repository.EmployedRepository
import com.vivcom.testShared.mockEmployed
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class FindEmployedByIdTest {

    private val employedRepository = mock(EmployedRepository::class.java)
    private lateinit var findEmployedById: FindEmployedById

    @Before
    fun setUp() {
        findEmployedById = FindEmployedById(employedRepository)
    }

    @After
    fun afterTest(){
        verifyNoMoreInteractions(employedRepository)
    }

    @Test
    operator fun invoke() {
        val employed = mockEmployed
        runBlocking {
            `when`(employedRepository.findEmployedById(1)).thenReturn(employed)

            val result = findEmployedById.invoke(1)

            verify(employedRepository).findEmployedById(1)
            assertEquals(employed, result)
        }
    }
}