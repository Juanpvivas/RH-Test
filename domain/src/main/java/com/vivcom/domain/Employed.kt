package com.vivcom.domain

data class Employed(
    val id: Int,
    val name: String,
    val position: String,
    val salary: Int,
    val phone: String,
    val email: String,
    val upperRelation: Int
)