package com.vivcom.rhtest.ui.detailEmployed

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.vivcom.testShared.mockEmployed
import com.vivcom.usecases.FindEmployedById
import com.vivcom.usecases.FindSubordinatesById
import com.vivcom.usecases.ToggleNewEmployed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

import org.mockito.Mockito.*

class DetailEmployedViewModelTest {
    private val findEmployedById = mock(FindEmployedById::class.java)
    private val findSubordinatesById = mock(FindSubordinatesById::class.java)
    private val toggleNewEmployed = mock(ToggleNewEmployed::class.java)
    private val observerModel = mock(Observer::class.java) as Observer<UiModel>
    private val observerModelList = mock(Observer::class.java) as Observer<UiModelList>
    private val _model = mock(MutableLiveData::class.java) as MutableLiveData<UiModel>

    private lateinit var detailEmployedViewModel: DetailEmployedViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        detailEmployedViewModel = DetailEmployedViewModel(
            1,
            findEmployedById,
            findSubordinatesById,
            toggleNewEmployed,
            uiDispatcher = Dispatchers.Unconfined
        )
    }

    @After
    fun afterTest() {
        verifyNoMoreInteractions(
            findEmployedById,
            findSubordinatesById,
            toggleNewEmployed,
            observerModel,
            observerModelList
        )
    }

    @Test
    fun `observing model and subordinates when call getModel`() {
        runBlocking {
            val employed = mockEmployed.copy(id = 1)
            val list = listOf(employed)
            `when`(findEmployedById.invoke(1)).thenReturn(employed)
            `when`(findSubordinatesById.invoke(1)).thenReturn(list)

            detailEmployedViewModel.model.observeForever(observerModel)
            detailEmployedViewModel.subordinates.observeForever(observerModelList)

            verify(observerModel).onChanged(UiModel(employed))
            verify(observerModelList).onChanged(UiModelList(list))
            verify(findEmployedById).invoke(1)
            verify(findSubordinatesById).invoke(1)
        }
    }

    @Test
    fun onNewEmployedClicked() {
        detailEmployedViewModel = DetailEmployedViewModel(
            1,
            findEmployedById,
            findSubordinatesById,
            toggleNewEmployed,
            _model = _model,
            uiDispatcher = Dispatchers.Unconfined
        )
        val employed = mockEmployed
        val uIModelEmployed = UiModel(employed)
        runBlocking {
            `when`(_model.value).thenReturn(uIModelEmployed)
            `when`(toggleNewEmployed.invoke(employed)).thenReturn(employed)

            detailEmployedViewModel.onNewEmployedClicked()

            verify(_model).value
            verify(toggleNewEmployed).invoke(employed)
        }
    }
}