package com.vivcom.rhtest.data.mappers

import com.vivcom.rhtest.data.local.Employed
import com.vivcom.domain.Employed as DomainEmployed

fun Employed.toDomainEmployed(): DomainEmployed =
    DomainEmployed(
        id,
        name,
        position,
        salary,
        phone,
        email,
        upperRelation,
        isNew
    )

fun DomainEmployed.toRoomEmployed(): Employed =
    Employed(
        id,
        name,
        position,
        salary,
        phone,
        email,
        upperRelation,
        isNew
    )