package com.example.myteamapphva.helper

import java.text.SimpleDateFormat
import java.util.*

class DateFormatHelper {

    fun formatToString(date: Date, format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(date)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }
}