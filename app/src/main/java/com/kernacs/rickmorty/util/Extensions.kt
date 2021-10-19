package com.kernacs.rickmorty.util

import java.time.Instant
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import java.util.*


fun String.isoStringToDate(): Date {
    val temporalAccessor: TemporalAccessor = DateTimeFormatter.ISO_INSTANT.parse(this)
    val instant: Instant = Instant.from(temporalAccessor)
    return Date.from(instant)
}

fun Date.toIsoString(): String {
    return DateTimeFormatter.ISO_INSTANT.format(this.toInstant())
}