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

        val dateString = "14-2-2022"

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

        val dateString = "20-02-2022"

        val resultExpected = 6L
        val result = Utils.getExpireDaysLeft(dateString)

        assertEquals(resultExpected, result)
    }

    @Test
    fun `Calculate Days Left If the Date is Before Current Means Expired`() {

        val dateString = "20-01-2022"

        val resultExpected = -1L
        val result = Utils.getExpireDaysLeft(dateString)

        assertEquals(resultExpected, result)
    }


    @Test
    fun `Calculate Days Left If the Date is After Current Means Not Expired`() {

        val dateString = "17-02-2022"
        val currentDateString = "22-01-2022"

        val resultExpected = 3L
        val result = Utils.getExpireDaysLeft(dateString)

        assertEquals(resultExpected, result)
    }


    @Test
    fun `Get All Items from DB AND Check expire days left THEN return the updated list`() {
        val itemList = listOf<ItemModel>(
            ItemModel(expireDate = "01-01-2022", isExpired = false, daysLeft = 10),
            ItemModel(expireDate = "01-02-2022", isExpired = false, daysLeft = 1),
            ItemModel(expireDate = "05-10-2021", isExpired = true, daysLeft = 0)
        )

        val expectedList = listOf<ItemModel>(
            ItemModel(expireDate = "01-01-2022", isExpired = true, daysLeft = 0),
            ItemModel(expireDate = "01-02-2022", isExpired = true, daysLeft = 0),
            ItemModel(expireDate = "05-10-2021", isExpired = true, daysLeft = 0)
        )

        val result = Utils.validateResult(itemList)

        assertEquals(expectedList, result)
    }
}