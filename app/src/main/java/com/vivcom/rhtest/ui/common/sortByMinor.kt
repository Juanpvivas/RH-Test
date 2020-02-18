package com.vivcom.rhtest.ui.common

import com.vivcom.domain.Employed

fun List<Employed>.sortByMinusSalary(): List<Employed> {
    val listOriginal = this.toMutableList()
    if (listOriginal.size > 1) {

        val nLeft = listOriginal.size / 2
        val nRight = listOriginal.size - nLeft
        var listLeft = ArrayList<Employed>()
        var listRight = ArrayList<Employed>()
        for (x in 0 until nLeft) {
            listLeft.add(listOriginal[x])
        }
        for (x in nLeft until nLeft + nRight) {
            listRight.add(listOriginal[x])
        }
        listLeft = listLeft.sortByMinusSalary() as ArrayList<Employed>
        listRight = listRight.sortByMinusSalary() as ArrayList<Employed>
        var indexLeft = 0
        var indexRight = 0
        var index = 0
        while (listLeft.size != indexLeft && listRight.size != indexRight) {
            if (listLeft[indexLeft].salary < listRight[indexRight].salary) {
                listOriginal[index] = listLeft[indexLeft]
                index++
                indexLeft++
            } else {
                listOriginal[index] = listRight[indexRight]
                index++
                indexRight++
            }
        }
        while (listLeft.size != indexLeft) {
            listOriginal[index] = listLeft[indexLeft]
            index++
            indexLeft++
        }
        while (listRight.size != indexRight) {
            listOriginal[index] = listRight[indexRight]
            index++
            indexRight++
        }
    }
    return listOriginal
}