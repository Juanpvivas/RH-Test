package com.vivcom.rhtest.data.network

import com.vivcom.data.repository.ResultData
import com.vivcom.data.source.RemoteDataSource
import org.json.JSONObject

class RemoteDataSourceImpl(private val employedApi: EmployedApi) : RemoteDataSource {
    override suspend fun getAllEmployed(): ResultData<JSONObject> =
        safeApiCall(
            call = { employedApi.getAllEmployed().callServices() },
            errorMessage = "Algo fallo al llamar al servicio"
        )
}