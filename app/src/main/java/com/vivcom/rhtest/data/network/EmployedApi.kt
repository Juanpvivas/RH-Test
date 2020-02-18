package com.vivcom.rhtest.data.network

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface EmployedApi {
    @GET("RH.json")
    fun getAllEmployedAsync(): Deferred<Response<Map<Any, Any>>>
}