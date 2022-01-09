package com.stambulo.learningassistant.utility

import java.text.SimpleDateFormat
import java.util.*

class DateUtility {
    fun isDatePeriod(start: String, end: String): Boolean {
        val now = Calendar.getInstance(TimeZone.getDefault())
        val nowHour = now[Calendar.HOUR_OF_DAY]
        val nowMinute = now[Calendar.MINUTE]
        val formatDate = SimpleDateFormat("HH:mm", Locale.getDefault())
        val startC = Calendar.getInstance(TimeZone.getDefault())
        startC.time = formatDate.parse(start)
        val startHour = startC[Calendar.HOUR_OF_DAY]
        val startMinute = startC[Calendar.MINUTE]
        val endC = Calendar.getInstance(TimeZone.getDefault())
        endC.time = formatDate.parse(end)
        val endHour = endC[Calendar.HOUR_OF_DAY]
        val endMinute = endC[Calendar.MINUTE]

        if (nowHour >= startHour
         && nowMinute >= startMinute
         && nowHour < endHour)
        {
            return true
        } else if (nowHour >= startHour
                && nowMinute >= startMinute
                && nowHour == endHour
                && nowMinute <= endMinute) {
            return true
        }
        return false
    }
}
