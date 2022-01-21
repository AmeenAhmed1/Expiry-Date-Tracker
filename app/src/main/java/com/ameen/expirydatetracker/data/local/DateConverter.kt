package com.ameen.expirydatetracker.data.local

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class DateConverter {

    private val dataFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)

    @TypeConverter
    fun toDate(value: String?) =
        value?.let { dataFormat.parse(it) }

    @TypeConverter
    fun fromDate(date: Date?) =
        date?.let { date.time }
}