package com.vivcom.rhtest.data.network

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter

class EmployedAdapter<T>: JsonAdapter<T>() {
    override fun fromJson(reader: JsonReader): T? {
        reader.beginObject()
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toJson(writer: JsonWriter, value: T?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}