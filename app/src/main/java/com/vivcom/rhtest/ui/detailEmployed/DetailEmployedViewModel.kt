package com.vivcom.rhtest.ui.detailEmployed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vivcom.usecases.FindEmployedById
import com.vivcom.usecases.ToggleNewEmployed
import kotlinx.coroutines.launch

class DetailEmployedViewModel(
    private val employedId: Int,
    private val findEmployedById: FindEmployedById,
    private val toggleNewEmployed: ToggleNewEmployed,
    private val _model: MutableLiveData<UiModel> = MutableLiveData()
) : ViewModel() {

    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) findEmployed()
            return _model
        }

    private fun findEmployed() = viewModelScope.launch {
        _model.value = UiModel(findEmployedById.invoke(employedId))
    }

    fun onNewEmployedClicked() = viewModelScope.launch {
        _model.value?.employed?.let {
            _model.value = UiModel(toggleNewEmployed.invoke(it))
        }
    }
}