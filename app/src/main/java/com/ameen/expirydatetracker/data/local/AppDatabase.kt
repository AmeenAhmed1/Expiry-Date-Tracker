package com.ameen.expirydatetracker.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ameen.expirydatetracker.data.ItemModel

@Database(entities = [ItemModel::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getItemDao(): ItemExpireDao

    companion object {
        @Volatile
        private var instanse: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instanse ?: synchronized(LOCK) {
            instanse ?: createDataBase(context).also { instanse = it }
        }

        private fun createDataBase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "itemExpireTracker.db"
            ).fallbackToDestructiveMigration().build()
    }
}