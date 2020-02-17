package com.vivcom.rhtest

import android.app.Application
import com.vivcom.data.repository.EmployedRepository
import com.vivcom.data.source.RemoteDataSource
import com.vivcom.rhtest.data.network.EmployedApi
import com.vivcom.rhtest.data.network.RemoteDataSourceImpl
import com.vivcom.rhtest.data.network.RetrofitBuild
import com.vivcom.rhtest.data.ui.MainActivity
import com.vivcom.rhtest.data.ui.MainViewModel
import com.vivcom.usecases.GetAllEmployed
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
}

val appModule = module {
    single { RetrofitBuild(androidContext().resources.getString(R.string.base_url)) }
    single { get<RetrofitBuild>().retrofit.create(EmployedApi::class.java) }
    factory<RemoteDataSource> { RemoteDataSourceImpl(get()) }
}

val dataModule = module {
    factory { EmployedRepository(remoteDataSource = get()) }
}
