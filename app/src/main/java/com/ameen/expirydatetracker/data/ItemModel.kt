package com.ameen.expirydatetracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "ScannedItems")
data class ItemModel(
    val title: String? = null,
    val category: String? = null,
    var isExpired: Boolean = false,
    val expireDate: String? = null
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}