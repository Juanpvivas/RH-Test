package com.vivcom.data.repository

import com.vivcom.data.source.LocalDataSource
import com.vivcom.data.source.RemoteDataSource
import com.vivcom.domain.Employed
import java.io.IOException
import kotlin.math.roundToInt

class EmployedRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {
    suspend fun getAllEmployed(): ResultData<List<Employed>> =
        if (localDataSource.isEmpty()) {
            callWSGetAllEmployed()
        } else {
            ResultData.Success(localDataSource.getAllEmployees())
        }

    private suspend fun callWSGetAllEmployed(): ResultData<List<Employed>> =
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
                    localDataSource.saveEmployees(listEmployed)
                    ResultData.Success(listEmployed)
                } else {
                    ResultData.Error(IOException("data vacia"))
                }
            }
            else -> result as ResultData.Error
        }

    private fun getDataOfEmployed(map: Map<*, *>, nameEmployed: String): Employed {
        return Employed(
            id = (map["id"] as Double).roundToInt(),
            name = nameEmployed,
            position = map["position"] as String,
            salary = map["salary"] as String,
            phone = map["phone"] as String,
            email = map["email"] as String,
            upperRelation = (map["upperRelation"] as Double).roundToInt(),
            isNew = false
        )
    }

    suspend fun findEmployedById(id: Int) = localDataSource.findById(id)

    suspend fun update(employed: Employed) = localDataSource.update(employed)

    suspend fun findSubordinatesById(id: Int) = localDataSource.findSubordinatesById(id)

    suspend fun getAllEmployedByIsNew(isNew: Boolean) =
        localDataSource.getAllEmployeesByIsNew(isNew)
}