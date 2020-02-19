package com.vivcom.rhtest.ui

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.vivcom.domain.Employed
import com.vivcom.rhtest.R
import com.vivcom.rhtest.ui.common.*
import com.vivcom.rhtest.ui.detailEmployed.DetailEmployedActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val viewModel: MainViewModel by currentScope.viewModel(this)
    private lateinit var adapter: EmployedAdapter
    private var listEmployees: List<Employed> = emptyList()
    private val listFilter = ArrayList<Employed>()
    private var queryFilter = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(tbProductsNovelties)
        initObservers()
        initRecycler()
        viewModel.getAllEmployed()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setOnQueryTextListener(this@MainActivity)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var listTemp: List<Employed> = if (queryFilter.isBlank()) listEmployees else listFilter

        listTemp = when (item.itemId) {
            R.id.filter_salary_mayor_to_minus -> listTemp.sortByMinusSalary().asReversed()
            R.id.filter_salary_minus_to_mayor -> listTemp.sortByMinusSalary()
            R.id.filter_news -> {
                viewModel.getNewsEmployees(true)
                listEmployees
            }
            R.id.filter_old -> {
                viewModel.getNewsEmployees(false)
                listEmployees
            }
            R.id.filter_all -> {
                viewModel.getAllEmployed()
                listEmployees
            }
            else -> listEmployees
        }

        showEmployees(listTemp)
        return true
    }

    private fun initRecycler() {
        adapter = EmployedAdapter(viewModel::onEmployedClicked)
        rclEmployees.adapter = adapter
    }

    private fun initObservers() {
        viewModel.mainStatus.observe(this, Observer(::managementStatus))
        viewModel.listEmployees.observe(this, Observer {
            progress.visibility = View.GONE
            listEmployees = it
            showEmployees(it)
        })
    }

    private fun showEmployees(employees: List<Employed>) {
        adapter.employees = employees
    }

    private fun managementStatus(mainStatus: MainStatus) {
        progress.visibility = if (mainStatus is MainStatus.Loading) View.VISIBLE else View.GONE

        when (mainStatus) {
            is MainStatus.Error ->
                alertDialog {
                    message = mainStatus.exception.message.toString()
                    cancelButton()
                }.show()
            is MainStatus.NavigationDetail -> startActivity<DetailEmployedActivity> {
                putExtra(DetailEmployedActivity.EMPLOYED, mainStatus.employed.id)
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        filter(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        queryFilter = newText.toString()
        filter(newText)
        return true
    }

    @SuppressLint("DefaultLocale")
    private fun filter(query: String?) {
        if (query.isNullOrBlank()) {
            showEmployees(listEmployees)
            return
        }
        listFilter.clear()
        listEmployees.forEach { employed ->
            if (employed.name.decapitalize().contains(query.decapitalize())) {
                listFilter.add(employed)
            }
        }
        showEmployees(listFilter)
    }
}
