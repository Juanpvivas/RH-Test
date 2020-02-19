package com.vivcom.rhtest.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Employed(
    @PrimaryKey
    val id: Int,
    val name: String,
    val position: String,
    val salary: String,
    val phone: String,
    val email: String,
    val upperRelation: Int,
    val isNew: Boolean
)