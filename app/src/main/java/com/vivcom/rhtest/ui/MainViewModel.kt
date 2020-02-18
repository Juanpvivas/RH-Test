package com.vivcom.rhtest.ui

import androidx.lifecycle.ViewModel
import com.vivcom.usecases.GetAllEmployed
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(private val getAllEmployed: GetAllEmployed) : ViewModel() {

    fun getAllEmployed() {
        viewModelScope.launch {
            val result = getAllEmployed.invoke()
        }
    }
}