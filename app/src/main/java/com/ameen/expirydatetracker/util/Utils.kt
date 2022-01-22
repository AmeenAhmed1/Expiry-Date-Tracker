package com.ameen.expirydatetracker.util

import android.os.Build
import androidx.annotation.RequiresApi
import com.ameen.expirydatetracker.data.ItemModel
import org.json.JSONObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

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
    fun checkScannedIsBeforeCurrent(date: LocalDate) =
        date.isBefore(getCurrentDate())

    /**
     *  Get the days between two dates
     *  @return days count if scannedDate is before current date.
     *  @return -1 otherwise.
     *  @param dateScanned -> Scanned Expire Date.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun getExpireDaysLeft(dateScanned: String): Long {

        val date = convertStringToDate(dateScanned)

        if (checkScannedIsBeforeCurrent(date))
            return ChronoUnit.DAYS.between(date, getCurrentDate())

        return -1L
    }

}