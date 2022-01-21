package com.ameen.expirydatetracker.util

import com.ameen.expirydatetracker.data.ItemModel
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

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

        assertEquals(result, itemExpected)
    }
}