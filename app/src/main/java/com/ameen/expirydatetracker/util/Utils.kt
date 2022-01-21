package com.ameen.expirydatetracker.util

import com.ameen.expirydatetracker.data.ItemModel
import org.json.JSONObject

object Utils {

    fun parsingScannedData(intentResult: String) =
        ItemModel(
            title = JSONObject(intentResult).getString("title"),
            category = JSONObject(intentResult).getString("category"),
            expireDate = JSONObject(intentResult).getString("date")
        )

    fun convertScannedData(dataResult: ItemModel): String {
        return "Title: ${dataResult.title},  Category: ${dataResult.category},  ExpireDate: ${dataResult.expireDate}"
    }
}