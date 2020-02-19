package com.vivcom.usecases

import com.vivcom.data.repository.EmployedRepository
import com.vivcom.data.repository.ResultData
import com.vivcom.domain.Employed
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class GetAllEmployedTest {

    private val employedRepository = mock(EmployedRepository::class.java)
    private lateinit var getAllEmployed: GetAllEmployed

    @Before
    fun setUp() {
        getAllEmployed = GetAllEmployed(employedRepository)
    }

    @After
    fun afterTest() {
        verifyNoMoreInteractions(employedRepository)
    }

    @Test
    fun invokeSuccess() {
        val resultData = ResultData.Success(emptyList<Employed>())
        runBlocking {
            `when`(employedRepository.getAllEmployed()).thenReturn(resultData)

            val result = getAllEmployed.invoke()

            verify(employedRepository).getAllEmployed()
            Assert.assertEquals(resultData, result)
        }
    }

    @Test
    fun invokeFailed() {
        val resultData = ResultData.Error(Exception())
        runBlocking {
            `when`(employedRepository.getAllEmployed()).thenReturn(resultData)

            val result = getAllEmployed.invoke()

            verify(employedRepository).getAllEmployed()
            Assert.assertEquals(resultData, result)
        }
    }
}