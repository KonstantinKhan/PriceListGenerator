package com.khan366kos.handler

import java.text.SimpleDateFormat

class CellParser {

    private val findCellRegex = ".*Реализация.*|.*Акт.*|.*Возврат.*".toRegex()
    private val findDateRegex = ".*\\d{2}.\\d{2}.\\d{4}.*".toRegex()
    private val findDeadlineRegex = ".*Период.*".toRegex()
    private val findScanRegex = ".*Есть факс.*".toRegex()

    val sdf = SimpleDateFormat("dd.MM.yyyy")

    fun findCell(str: String): String? = findCellRegex.matchEntire(str)?.value?.let {
        findDate(it)
    }

    private fun findDate(str: String): String? = run {
        str.split(" ")
            .forEach {
                findDateRegex.matchEntire(it)?.let { result ->
                    return@run result.value
                }
            }
        return@run null
    }


    fun findDeadline(str: String): String? = findDeadlineRegex.matchEntire(str)?.value?.substringAfter("- ")

    fun findScan(str: String): String? = findScanRegex.matchEntire(str)?.value
}