package com.vivcom.rhtest.ui.detailEmployed

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.vivcom.rhtest.R
import com.vivcom.rhtest.ui.common.loadUrl
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*
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
        fabIsNew.setOnClickListener { viewModel.onNewEmployedClicked() }
    }

    private fun iniObservers() {
        viewModel.model.observe(this, Observer(::updateUI))
    }

    private fun updateUI(modelUI: UiModel) {
        with(modelUI.employed) {
            toolbar.title = name
            imgDetailImage.loadUrl("http://placeimg.com/640/480/tech/${id}")
            txvEmployedId.text = getString(R.string.id, id)
            txvName.text = getString(R.string.name, name)
            txvPosition.text = getString(R.string.position, position)
            txvPhone.text = getString(R.string.phone, phone)
            txvSalary.text = getString(R.string.salary, salary)
            txvEmail.text = getString(R.string.email, email)

            val icon = if (isNew) R.drawable.ic_new else R.drawable.ic_old_employed
            fabIsNew.setImageDrawable(getDrawable(icon))
        }
    }
}
