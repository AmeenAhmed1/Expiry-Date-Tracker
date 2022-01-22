package com.ameen.expirydatetracker.util

import com.ameen.expirydatetracker.data.ItemModel
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RunWith(JUnit4::class)
class UtilsTest {

    @Test
    fun parsingScannedData() {

        val itemExpected =
            ItemModel(title = "title", category = "category", expireDate = "20-20-2022")

        val json = JSONObject().apply {
            put("title", "title")
            put("category", "category")
            put("date", "20-20-2022")
        }.toString()

        val result = Utils.parsingScannedData(json)

        assertEquals(itemExpected, result)
    }

    @Test
    fun dateFormatFromDateString() {

        val dateString = "20-11-2022"

        val dateExpected = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd-M-yyyy"))

        val dateResult = Utils.convertStringToDate(dateString)

        assertEquals(dateExpected, dateResult)
    }

    @Test
    fun getCurrentDateFromLocal() {

        val dateString = "22-1-2022"

        val dateExpected = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd-M-yyyy"))

        val dateResult = Utils.getCurrentDate()

        assertEquals(dateExpected, dateResult)
    }

    @Test
    fun checkDateIsBeforeCurrent() {

        val dateString = "20-1-2022"
        val date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd-M-yyyy"))

        val result = Utils.checkScannedIsBeforeCurrent(date)

        assert(result)
    }


    @Test
    fun calculateDaysLeft() {

        val dateString = "20-01-2022"

        val resultExpected = 2L
        val result = Utils.getExpireDaysLeft(dateString)

        assertEquals(resultExpected, result)
    }
}