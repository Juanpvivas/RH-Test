package com.vivcom.rhtest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vivcom.usecases.GetAllEmployed
import androidx.lifecycle.viewModelScope
import com.vivcom.data.repository.ResultData
import com.vivcom.domain.Employed
import kotlinx.coroutines.launch

class MainViewModel(
    private val getAllEmployed: GetAllEmployed,
    private val _mainStatus: MutableLiveData<MainStatus> = MutableLiveData(),
    private val _listEmployees: MutableLiveData<List<Employed>> = MutableLiveData()
) : ViewModel() {

    val mainStatus: LiveData<MainStatus> = _mainStatus
    val listEmployees: LiveData<List<Employed>> = _listEmployees


    fun getAllEmployed() {
        viewModelScope.launch {
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
}