package com.vivcom.rhtest

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initDI()
    }
}