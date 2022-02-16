package com.ameen.expirydatetracker.util

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.ameen.expirydatetracker.data.ItemModel
import org.json.JSONObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.math.abs

@RequiresApi(Build.VERSION_CODES.O)
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


    /**
     * Converting date scanned from String into Date()
     * in formation [ Day-Month-Year ].
     * @param scannedDate -> The scanned String.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun convertStringToDate(scannedDate: String) =
        LocalDate.parse(scannedDate, DateTimeFormatter.ofPattern("dd-M-yyyy"))


    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentDate() = LocalDate.now()


    @RequiresApi(Build.VERSION_CODES.O)
    fun checkScannedIsBeforeCurrent(date: LocalDate, currentDate: LocalDate = getCurrentDate()) =
        date.isBefore(currentDate)

    /**
     *  Get the days between two dates
     *  @return days count if scannedDate is before current date.
     *  @return -1 otherwise.
     *  @param dateScanned -> Scanned Expire Date.
     */
    fun getExpireDaysLeft(dateScanned: String, currentDate: LocalDate = getCurrentDate()): Long {

        val date = convertStringToDate(dateScanned)

        if (!checkScannedIsBeforeCurrent(date, currentDate))
            return abs(ChronoUnit.DAYS.between(date, currentDate))

        return -1
    }

    /**
     * Check the days left for expiration then update the DB.
     * @param result --> The Current DB items.
     * @return result --> The Updated List.
     */
    fun validateResult(result: List<ItemModel>): List<ItemModel> {
        for (singleItem in result) {
            val daysLeft = getExpireDaysLeft(singleItem.expireDate!!)

            if (daysLeft > -1L) singleItem.daysLeft = daysLeft
            else {
                singleItem.daysLeft = 0
                singleItem.isExpired = true
            }
        }
        return result
    }
}