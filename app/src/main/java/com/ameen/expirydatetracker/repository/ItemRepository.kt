package com.ameen.expirydatetracker.repository

import com.ameen.expirydatetracker.data.ItemModel
import com.ameen.expirydatetracker.data.local.AppDatabase

class ItemRepository(private val db: AppDatabase) {

    suspend fun insertItemLocally(item: ItemModel) =
        db.getItemDao().insertScannedItem(item)
}