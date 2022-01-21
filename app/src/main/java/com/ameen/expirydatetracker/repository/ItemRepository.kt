package com.ameen.expirydatetracker.repository

import com.ameen.expirydatetracker.data.ItemModel
import com.ameen.expirydatetracker.data.local.AppDatabase

class ItemRepository(private val db: AppDatabase) {

    suspend fun insertItemLocally(item: ItemModel) =
        db.getItemDao().insertScannedItem(item)

    suspend fun getAllScannedItemLocally() =
        db.getItemDao().getScannedItem()

    suspend fun getAllExpiredItemLocally() =
        db.getItemDao().getExpiredItems()
}