package com.vivcom.rhtest

import android.app.Application
import com.vivcom.data.repository.EmployedRepository
import com.vivcom.data.source.LocalDataSource
import com.vivcom.data.source.RemoteDataSource
import com.vivcom.rhtest.data.local.RHDataBase
import com.vivcom.rhtest.data.local.EmployedDaoDataSource
import com.vivcom.rhtest.data.network.EmployedApi
import com.vivcom.rhtest.data.network.RemoteDataSourceImpl
import com.vivcom.rhtest.data.network.RetrofitBuild
import com.vivcom.rhtest.ui.main.MainActivity
import com.vivcom.rhtest.ui.main.MainViewModel
import com.vivcom.rhtest.ui.detailEmployed.DetailEmployedActivity
import com.vivcom.rhtest.ui.detailEmployed.DetailEmployedViewModel
import com.vivcom.usecases.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.initDI() {
    startKoin {
        androidLogger()
        androidContext(this@initDI)
        modules(listOf(appModule, dataModule, scopesModule))
    }
}

private val scopesModule = module {
    scope(named<MainActivity>()) {
        viewModel {
            MainViewModel(
                getAllEmployed = get(),
                getAllEmployedByIsNew = get(),
                uiDispatcher = get()
            )
        }
        scoped { GetAllEmployed(employedRepository = get()) }
        scoped { GetAllEmployedByIsNew(employedRepository = get()) }
    }

    scope(named<DetailEmployedActivity>()) {
        viewModel { (id: Int) ->
            DetailEmployedViewModel(
                id,
                findEmployedById = get(),
                toggleNewEmployed = get(),
                findSubordinatesById = get(),
                uiDispatcher = get()
            )
        }
        scoped { FindEmployedById(employedRepository = get()) }
        scoped { ToggleNewEmployed(employedRepository = get()) }
        scoped { FindSubordinatesById(employedRepository = get()) }
    }
}

val appModule = module {
    single { RetrofitBuild(androidContext().resources.getString(R.string.base_url)) }
    single { get<RetrofitBuild>().retrofit.create(EmployedApi::class.java) }
    single { RHDataBase.build(get()).employedDao() }
    single<CoroutineDispatcher> { Dispatchers.Main }
    factory<RemoteDataSource> { RemoteDataSourceImpl(get()) }
    factory<LocalDataSource> { EmployedDaoDataSource(get()) }
}

val dataModule = module {
    factory { EmployedRepository(remoteDataSource = get(), localDataSource = get()) }
}
