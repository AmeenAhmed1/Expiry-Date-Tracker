package com.ameen.expirydatetracker.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ameen.expirydatetracker.data.ItemModel
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    private lateinit var db: AppDatabase
    private lateinit var dbDao: ItemExpireDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dbDao = db.getItemDao()

        //insert some data to DB.
        val data: ItemModel = ItemModel(title = "First", category = "First")
        val dataExpire: ItemModel =
            ItemModel(title = "Second", category = "Second", isExpired = true)

        runBlocking {
            for (i in 1..6) dbDao.insertScannedItem(data)
            for (i in 1..3) dbDao.insertScannedItem(dataExpire)
        }
    }

    @After
    @Throws(IOException::class)
    fun closeDataBase() {
        db.close()
    }

    @Test
    fun insertScannedItem() = runBlocking {
        val data: ItemModel = ItemModel(title = "First", category = "First")
        val items = dbDao.getScannedItem()
        assertThat(items.contains(data), equalTo(true))
    }

    @Test
    fun getAllScannedNotExpiredItem() = runBlocking {
        val items = dbDao.getScannedItem()
        assertThat(items.size, equalTo(6))
    }

    @Test
    fun getAllExpiredItems() = runBlocking {
        val items = dbDao.getExpiredItems()
        assertThat(items.size, equalTo(3))
    }
}