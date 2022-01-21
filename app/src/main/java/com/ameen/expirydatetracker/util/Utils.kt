package com.ameen.expirydatetracker.util

import com.ameen.expirydatetracker.data.ItemModel
import org.json.JSONObject

object Utils {

    /**
     * Converting scanned data to an ItemModel Object
     * @param intentResult -> the JSON scanned data.
     */
    fun parsingScannedData(intentResult: String) =
        ItemModel(
            title = JSONObject(intentResult).getString("title"),
            category = JSONObject(intentResult).getString("category"),
            expireDate = JSONObject(intentResult).getString("date")
        )

    /**
     * Converting the object to String so we can show it up in the dialog.
     * @param dataResult -> The object we convert to String.
     */
    fun convertScannedData(dataResult: ItemModel): String {
        return "Title: ${dataResult.title},  Category: ${dataResult.category},  ExpireDate: ${dataResult.expireDate}"
    }
}