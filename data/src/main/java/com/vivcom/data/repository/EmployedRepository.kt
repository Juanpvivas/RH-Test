package com.vivcom.data.repository

import com.vivcom.data.source.RemoteDataSource
import com.vivcom.domain.Employed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import kotlin.math.roundToInt

class EmployedRepository(private val remoteDataSource: RemoteDataSource) {
    suspend fun getAllEmployed(): ResultData<List<Employed>> =
        withContext(Dispatchers.IO) {
            when (val result = remoteDataSource.getAllEmployed()) {
                is ResultData.Success -> {
                    if (result.data.isNotEmpty()) {
                        val listEmployed = ArrayList<Employed>()
                        result.data.keys.forEach { nameEmployed ->
                            val employed = getDataOfEmployed(
                                result.data[nameEmployed] as Map<*, *>,
                                nameEmployed.toString()
                            )
                            listEmployed.add(employed)
                        }
                        ResultData.Success(listEmployed)
                    } else {
                        ResultData.Error(IOException("data vacia"))
                    }
                }
                else -> result as ResultData.Error
            }
        }

    private fun getDataOfEmployed(map: Map<*, *>, nameEmployed: String): Employed {
        return Employed(
            id = (map["id"] as Double).roundToInt(),
            name = nameEmployed,
            position = map["position"] as String,
            salary = map["salary"] as String,
            phone = map["phone"] as String,
            email = map["email"] as String,
            upperRelation = (map["upperRelation"] as Double).roundToInt()
        )
    }
}