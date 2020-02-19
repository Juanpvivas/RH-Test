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
import com.vivcom.rhtest.ui.MainActivity
import com.vivcom.rhtest.ui.MainViewModel
import com.vivcom.rhtest.ui.detailEmployed.DetailEmployedActivity
import com.vivcom.rhtest.ui.detailEmployed.DetailEmployedViewModel
import com.vivcom.usecases.FindEmployedById
import com.vivcom.usecases.GetAllEmployed
import com.vivcom.usecases.ToggleNewEmployed
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
        viewModel { MainViewModel(getAllEmployed = get()) }
        scoped { GetAllEmployed(employedRepository = get()) }
    }

    scope(named<DetailEmployedActivity>()) {
        viewModel { (id: Int) -> DetailEmployedViewModel(id, findEmployedById = get(), toggleNewEmployed = get()) }
        scoped { FindEmployedById(employedRepository = get()) }
        scoped { ToggleNewEmployed(employedRepository = get()) }
    }
}

val appModule = module {
    single { RetrofitBuild(androidContext().resources.getString(R.string.base_url)) }
    single { get<RetrofitBuild>().retrofit.create(EmployedApi::class.java) }
    single { RHDataBase.build(get()).employedDao() }
    factory<RemoteDataSource> { RemoteDataSourceImpl(get()) }
    factory<LocalDataSource> { EmployedDaoDataSource(get()) }
}

val dataModule = module {
    factory { EmployedRepository(remoteDataSource = get(), localDataSource = get()) }
}
