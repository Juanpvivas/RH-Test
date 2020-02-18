package com.vivcom.data.source

import com.vivcom.data.repository.ResultData

interface RemoteDataSource {
    suspend fun getAllEmployed(): ResultData<Map<Any, Any>>
}