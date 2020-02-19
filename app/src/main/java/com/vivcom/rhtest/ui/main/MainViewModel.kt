package com.vivcom.rhtest.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vivcom.data.repository.ResultData
import com.vivcom.domain.Employed
import com.vivcom.rhtest.ui.common.ScopedViewModel
import com.vivcom.usecases.GetAllEmployed
import com.vivcom.usecases.GetAllEmployedByIsNew
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class MainViewModel(
    private val getAllEmployed: GetAllEmployed,
    private val getAllEmployedByIsNew: GetAllEmployedByIsNew,
    private val _mainStatus: MutableLiveData<MainStatus> = MutableLiveData(),
    private val _listEmployees: MutableLiveData<List<Employed>> = MutableLiveData(),
    override val uiDispatcher: CoroutineDispatcher
) : ScopedViewModel(uiDispatcher) {

    val mainStatus: LiveData<MainStatus> = _mainStatus
    val listEmployees: LiveData<List<Employed>> = _listEmployees


    fun getAllEmployed() {
        launch {
            _mainStatus.value = MainStatus.Loading
            when (val result = getAllEmployed.invoke()) {
                is ResultData.Success -> _listEmployees.value = result.data
                is ResultData.Error -> _mainStatus.value = MainStatus.Error(result.exception)
            }
        }
    }

    fun onEmployedClicked(employed: Employed) {
        _mainStatus.value = MainStatus.NavigationDetail(employed)
    }

    fun getNewsEmployees(isNew: Boolean) {
        launch {
            _listEmployees.value = getAllEmployedByIsNew.invoke(isNew)
        }
    }
}
