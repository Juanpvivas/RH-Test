package com.vivcom.rhtest.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.vivcom.data.repository.ResultData
import com.vivcom.domain.Employed
import com.vivcom.rhtest.ui.main.MainStatus
import com.vivcom.rhtest.ui.main.MainViewModel
import com.vivcom.testShared.mockEmployed
import com.vivcom.usecases.GetAllEmployed
import com.vivcom.usecases.GetAllEmployedByIsNew
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*
import java.lang.Exception


class MainViewModelTest {

    private val getAllEmployed = mock(GetAllEmployed::class.java)
    private val getAllEmployedByIsNew = mock(GetAllEmployedByIsNew::class.java)
    private val observerMainStatus = mock(Observer::class.java) as Observer<MainStatus>
    private val observerListEmployees = mock(Observer::class.java) as Observer<List<Employed>>
    private val _mainStatus = mock(MutableLiveData::class.java) as MutableLiveData<MainStatus>
    private val _listEmployees =
        mock(MutableLiveData::class.java) as MutableLiveData<List<Employed>>

    private lateinit var mainViewModel: MainViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        mainViewModel =
            MainViewModel(
                getAllEmployed,
                getAllEmployedByIsNew,
                _mainStatus,
                _listEmployees,
                Dispatchers.Unconfined
            )
    }

    @After
    fun afterTest() {
        verifyNoMoreInteractions(
            getAllEmployed,
            getAllEmployedByIsNew,
            observerMainStatus,
            observerListEmployees,
            _mainStatus,
            _listEmployees
        )
    }

    @Test
    fun `observing LiveData mainStatus when _mainStatus change`() {
        mainViewModel = MainViewModel(
            getAllEmployed,
            getAllEmployedByIsNew,
            uiDispatcher = Dispatchers.Unconfined
        )
        val employed = mockEmployed
        mainViewModel.mainStatus.observeForever(observerMainStatus)

        mainViewModel.onEmployedClicked(employed)

        verify(observerMainStatus).onChanged(ArgumentMatchers.any())
    }

    @Test
    fun `observing LiveData listEmployees when _listEmployees change`() {
        mainViewModel = MainViewModel(
            getAllEmployed,
            getAllEmployedByIsNew,
            uiDispatcher = Dispatchers.Unconfined
        )
        runBlocking {
            mainViewModel.listEmployees.observeForever(observerListEmployees)

            mainViewModel.getNewsEmployees(true)
            verify(getAllEmployedByIsNew).invoke(true)
            verify(observerListEmployees).onChanged(ArgumentMatchers.any())
        }
    }


    @Test
    fun getAllEmployedSuccess() {
        val list = listOf(mockEmployed.copy(1), mockEmployed.copy(2))
        val resultData = ResultData.Success(list)
        val argumentLoading = ArgumentCaptor.forClass(MainStatus.Loading::class.java)
        runBlocking {
            `when`(getAllEmployed.invoke()).thenReturn(resultData)
            mainViewModel.getAllEmployed()
            verify(_mainStatus).value = argumentLoading.capture()
            verify(getAllEmployed).invoke()
            verify(_listEmployees).value = resultData.data
        }
    }

    @Test
    fun getAllEmployedFailed() {
        val resultData = ResultData.Error(Exception())
        val argumentLoading = ArgumentCaptor.forClass(MainStatus.Loading::class.java)
        runBlocking {
            `when`(getAllEmployed.invoke()).thenReturn(resultData)
            mainViewModel.getAllEmployed()
            verify(getAllEmployed).invoke()
            verify(_mainStatus, times(2)).value = argumentLoading.capture()
        }
    }

    @Test
    fun onEmployedClicked() {
        val argumentNavigation = ArgumentCaptor.forClass(MainStatus.NavigationDetail::class.java)
        val employed = mockEmployed
        mainViewModel.onEmployedClicked(employed)
        verify(_mainStatus).value = argumentNavigation.capture()
        val value = argumentNavigation.value
        Assert.assertEquals(employed, value.employed)
    }

    @Test
    fun getNewsEmployees() {
        val list = listOf(mockEmployed.copy(1), mockEmployed.copy(2))
        runBlocking {
            `when`(getAllEmployedByIsNew.invoke(true)).thenReturn(list)
            mainViewModel.getNewsEmployees(true)
            verify(getAllEmployedByIsNew).invoke(true)
            verify(_listEmployees).value = list
        }
    }
}