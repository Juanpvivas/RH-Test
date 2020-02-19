package com.vivcom.rhtest.data.network

import com.vivcom.data.repository.ResultData
import com.vivcom.data.source.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteDataSourceImpl(private val employedApi: EmployedApi) : RemoteDataSource {
    override suspend fun getAllEmployed(): ResultData<Map<Any, Any>> =
        withContext(Dispatchers.IO) {
            safeApiCall(
                call = { employedApi.getAllEmployedAsync().await().callServices() },
                errorMessage = "Algo fallo al llamar al servicio"
            )
        }
}