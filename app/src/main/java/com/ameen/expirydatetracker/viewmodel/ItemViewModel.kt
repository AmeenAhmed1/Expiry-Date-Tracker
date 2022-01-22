package com.ameen.expirydatetracker.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ameen.expirydatetracker.data.ItemModel
import com.ameen.expirydatetracker.repository.ItemRepository
import kotlinx.coroutines.launch

class ItemViewModel(private val itemRepository: ItemRepository) : ViewModel() {

    private val TAG = "ItemViewModel"

    val scannedItem: MutableLiveData<List<ItemModel>> = MutableLiveData()
    val expiredItem: MutableLiveData<List<ItemModel>> = MutableLiveData()

    fun insertItemLocally(item: ItemModel) = viewModelScope.launch {
        itemRepository.insertItemLocally(item)?.also {
            getAllScannedItemLocally()
        }
    }

    fun getAllScannedItemLocally() = viewModelScope.launch {
        Log.i(TAG, "getAllScannedItemLocally: Called")
        val result = itemRepository.getAllScannedItemLocally()
        scannedItem.postValue(result)
    }

    fun getAllExpiredItemLocally() = viewModelScope.launch {
        val result = itemRepository.getAllExpiredItemLocally()
        expiredItem.postValue(result)
    }
}