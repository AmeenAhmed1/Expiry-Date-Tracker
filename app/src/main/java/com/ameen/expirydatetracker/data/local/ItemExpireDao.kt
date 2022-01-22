package com.ameen.expirydatetracker.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ameen.expirydatetracker.data.ItemModel

@Dao
interface ItemExpireDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScannedItem(item: ItemModel): Long?

    @Query("SELECT * FROM ScannedItems WHERE isExpired = 0 ORDER BY daysLeft ASC")
    suspend fun getScannedItem(): List<ItemModel>

    @Query("SELECT * FROM ScannedItems WHERE isExpired = 1")
    suspend fun getExpiredItems(): List<ItemModel>
}