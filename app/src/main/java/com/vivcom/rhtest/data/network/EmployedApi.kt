package com.vivcom.rhtest.data.network

import retrofit2.Response
import retrofit2.http.GET

interface EmployedApi {
    @GET("RH.json")
    fun getAllEmployed(): Response<String>
}