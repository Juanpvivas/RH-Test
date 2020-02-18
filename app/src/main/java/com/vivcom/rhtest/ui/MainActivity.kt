package com.vivcom.rhtest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vivcom.rhtest.R
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by currentScope.viewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.getAllEmployed()
    }
}
