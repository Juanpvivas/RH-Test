package com.vivcom.rhtest.ui.detailEmployed

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.vivcom.rhtest.R
import kotlinx.android.synthetic.main.activity_detail.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailEmployedActivity : AppCompatActivity() {
    companion object {
        const val EMPLOYED = "employed"
    }

    private val viewModel: DetailEmployedViewModel by currentScope.viewModel(this) {
        parametersOf(intent.getIntExtra(EMPLOYED, -1))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        iniObservers()
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    private fun iniObservers() {
        viewModel.model.observe(this, Observer { })
    }
}
