package com.vivcom.usecases

import com.vivcom.data.repository.EmployedRepository
import com.vivcom.testShared.mockEmployed
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito.*

class ToggleNewEmployedTest {

    private val employedRepository = mock(EmployedRepository::class.java)
    private lateinit var toggleNewEmployed: ToggleNewEmployed

    @Before
    fun setUp() {
        toggleNewEmployed = ToggleNewEmployed(employedRepository)
    }

    @After
    fun afterTest() {
        verifyNoMoreInteractions(employedRepository)
    }

    @Test
    fun `verify call toggleNewEmployed`() {
        val employed = mockEmployed
        runBlocking {

            val result = toggleNewEmployed.invoke(employed)

            verify(employedRepository).update(result)
        }
    }

    @Test
    fun `verify set isNew true`() {
        val employed = mockEmployed.copy(isNew = false)
        runBlocking {

            val result = toggleNewEmployed.invoke(employed)

            verify(employedRepository).update(result)
            assertEquals(true, result.isNew)
        }
    }

    @Test
    fun `verify set isNew false`() {
        val employed = mockEmployed.copy(isNew = true)
        runBlocking {

            val result = toggleNewEmployed.invoke(employed)

            verify(employedRepository).update(result)
            assertEquals(false, result.isNew)
        }
    }
}