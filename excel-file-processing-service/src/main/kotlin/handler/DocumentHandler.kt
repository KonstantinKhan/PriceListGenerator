package com.khan366kos.handler

import java.time.Instant
import java.time.temporal.ChronoUnit


class DocumentHandler(private val period: Int) {

    fun isExpired(expiredDate: Instant, returnDate: Instant): Boolean =
        ChronoUnit.DAYS.between(returnDate, expiredDate) > period

    fun isScan(): Boolean = true

    fun isOk(): Boolean = true
}