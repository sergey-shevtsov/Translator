package com.sshevtsov.translator.presentation.history.util

import java.text.SimpleDateFormat
import java.util.*

object TimestampFormatter {

    fun formatTimestampToHistoryString(timestamp: Long): String {
        val pattern = if (isToday(timestamp)) "hh:mm" else "dd.MM.yyyy"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        return sdf.format(timestamp)
    }

    private fun isToday(timestamp: Long): Boolean {
        val currentDateCalendar = Calendar.getInstance().apply {
            this.timeInMillis = System.currentTimeMillis()
        }
        val currentYear = currentDateCalendar.get(Calendar.YEAR)
        val currentMonth = currentDateCalendar.get(Calendar.MONTH)
        val currentDay = currentDateCalendar.get(Calendar.DAY_OF_MONTH)

        val comparableCalendar = Calendar.getInstance().apply {
            this.timeInMillis = timestamp
        }
        val comparableYear = comparableCalendar.get(Calendar.YEAR)
        val comparableMonth = comparableCalendar.get(Calendar.MONTH)
        val comparableDay = comparableCalendar.get(Calendar.DAY_OF_MONTH)

        return currentYear == comparableYear &&
                currentMonth == comparableMonth &&
                currentDay == comparableDay
    }
}