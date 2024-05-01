package com.example.biljart.util

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object DateUtils {
    // Convert a date string in ISO format to a formatted date string, default format is "dd-MM-yyyy"
    fun formatDateFromIso(isoDateTime: String, format: String = "dd-MM-yyyy"): String {
        val zonedDateTime = ZonedDateTime.parse(isoDateTime)
        return zonedDateTime.format(DateTimeFormatter.ofPattern(format))
    }
}
