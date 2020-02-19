package com.vivcom.data.repository

import com.vivcom.data.source.LocalDataSource
import com.vivcom.data.source.RemoteDataSource
import com.vivcom.testShared.mockEmployed
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*

class EmployedRepositoryTest {
    private val remoteDataSource = mock(RemoteDataSource::class.java)
    private val localDataSource = mock(LocalDataSource::class.java)

    private lateinit var employedRepository: EmployedRepository

    @After
    fun afterTest() {
        verifyNoMoreInteractions(localDataSource, remoteDataSource)
    }

    @Before
    fun setUp() {
        employedRepository = EmployedRepository(remoteDataSource, localDataSource)
    }

    @Test
    fun `getAllEmployed when localDataSource is not empty`() {
        val list = listOf(mockEmployed, mockEmployed)
        runBlocking {
            `when`(localDataSource.isEmpty()).thenReturn(false)
            `when`(localDataSource.getAllEmployees()).thenReturn(list)

            val result = employedRepository.getAllEmployed()

            verify(localDataSource).isEmpty()
            verify(localDataSource).getAllEmployees()
            assert(result is ResultData.Success)
            assertEquals(list, (result as ResultData.Success).data)
        }
    }

    @Test
    fun `getAllEmployed when localDataSource is empty and ResultData is Error`() {
        val resultData = ResultData.Error(Exception())
        runBlocking {
            `when`(localDataSource.isEmpty()).thenReturn(true)
            `when`(remoteDataSource.getAllEmployed()).thenReturn(resultData)

            val result = employedRepository.getAllEmployed()

            verify(localDataSource).isEmpty()
            verify(remoteDataSource).getAllEmployed()
            assert(result is ResultData.Error)
            assertEquals(resultData.exception, (result as ResultData.Error).exception)
        }
    }

    @Test
    fun `getAllEmployed when localDataSource is empty and ResultData is Success and list is empty`() {
        val resultData = ResultData.Success(emptyMap<Any, Any>())
        runBlocking {
            `when`(localDataSource.isEmpty()).thenReturn(true)
            `when`(remoteDataSource.getAllEmployed()).thenReturn(resultData)

            val result = employedRepository.getAllEmployed()

            verify(localDataSource).isEmpty()
            verify(remoteDataSource).getAllEmployed()
            assert(result is ResultData.Error)
            assertEquals("data vacia", (result as ResultData.Error).exception.message)
        }
    }

    @Test
    fun `getAllEmployed when localDataSource is empty and ResultData is Success and list is no empty`() {
        val resultData = ResultData.Success(
            mutableMapOf(
                Pair<Any, Any>(
                    "Name",
                    mutableMapOf(
                        Pair("id", 1.0),
                        Pair("position", "testPosition"),
                        Pair("salary", "testSalary"),
                        Pair("phone", "testPhone"),
                        Pair("email", "testEmail"),
                        Pair("upperRelation", 0.0)
                    )
                )
            )
        )
        runBlocking {
            `when`(localDataSource.isEmpty()).thenReturn(true)
            `when`(remoteDataSource.getAllEmployed()).thenReturn(resultData)

            val result = employedRepository.getAllEmployed()

            verify(localDataSource).isEmpty()
            verify(remoteDataSource).getAllEmployed()
            verify(localDataSource).saveEmployees(ArgumentMatchers.anyList())
            assert(result is ResultData.Success)
            val data = (result as ResultData.Success).data
            assertEquals(1, data[0].id)
            assertEquals("testPosition", data[0].position)
            assertEquals("testSalary", data[0].salary)
            assertEquals("testPhone", data[0].phone)
            assertEquals("testEmail", data[0].email)
            assertEquals(0, data[0].upperRelation)
            assertEquals(false, data[0].isNew)
        }
    }

    @Test
    fun findEmployedById() {
        val employed = mockEmployed
        runBlocking {
            `when`(localDataSource.findById(1)).thenReturn(employed)

            val result = employedRepository.findEmployedById(1)

            verify(localDataSource).findById(1)
            assertEquals(employed, result)
        }
    }

    @Test
    fun update() {
        val employed = mockEmployed
        runBlocking {
            employedRepository.update(employed)

            verify(localDataSource).update(employed)
        }
    }

    @Test
    fun findSubordinatesById() {
        val employees = listOf(mockEmployed, mockEmployed)
        runBlocking {
            `when`(localDataSource.findSubordinatesById(1)).thenReturn(employees)
            val result = employedRepository.findSubordinatesById(1)
            verify(localDataSource).findSubordinatesById(1)
            assertEquals(employees, result)
        }
    }

    @Test
    fun getAllEmployedByIsNew() {
        val employees = listOf(mockEmployed, mockEmployed)
        runBlocking {
            `when`(localDataSource.getAllEmployeesByIsNew(true)).thenReturn(employees)
            val result = employedRepository.getAllEmployedByIsNew(true)
            verify(localDataSource).getAllEmployeesByIsNew(true)
            assertEquals(employees, result)
        }
    }
}