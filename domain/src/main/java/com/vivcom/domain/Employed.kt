package com.vivcom.domain

data class Employed(
    val id: Int,
    val name: String,
    val position: String,
    val salary: String,
    val phone: String,
    val email: String,
    val upperRelation: Int,
    val isNew: Boolean
) {
    val description = "nombre: $name, cargo: $position, salario: $salary"
}