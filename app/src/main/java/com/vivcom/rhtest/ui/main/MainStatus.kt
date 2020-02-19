package com.vivcom.rhtest.ui.main

import com.vivcom.domain.Employed
import java.lang.Exception

sealed class MainStatus {
    object Loading: MainStatus()
    class Error(val exception: Exception) : MainStatus()
    class NavigationDetail(val employed: Employed): MainStatus()
}