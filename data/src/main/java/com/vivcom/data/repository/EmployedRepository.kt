package com.vivcom.data.repository

import com.vivcom.data.source.RemoteDataSource
import com.vivcom.domain.Employed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class EmployedRepository(private val remoteDataSource: RemoteDataSource) {
    suspend fun getAllEmployed(): ResultData<List<Employed>> =
        withContext(Dispatchers.IO) {
            when (val result = remoteDataSource.getAllEmployed()) {
                is ResultData.Success -> {
                    if (result.data != null) {
                        ResultData.Success(emptyList<Employed>())
                    } else {
                        ResultData.Error(IOException("data vacia"))
                    }
                }
                else -> result as ResultData.Error
            }
        }
}