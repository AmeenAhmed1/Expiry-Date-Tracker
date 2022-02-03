package com.ameen.expirydatetracker.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ameen.expirydatetracker.data.ItemModel
import com.ameen.expirydatetracker.repository.ItemRepository
import com.ameen.expirydatetracker.util.Utils
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class ItemViewModel(private val itemRepository: ItemRepository) : ViewModel() {

    private val TAG = "ItemViewModel"

    val scannedItem: MutableLiveData<List<ItemModel>> = MutableLiveData()
    val expiredItem: MutableLiveData<List<ItemModel>> = MutableLiveData()

    init {
        viewModelScope.launch {
            itemRepository.insertListOfItemLocally(
                Utils.validateResult(itemRepository.getAllItems())
            )
        }
    }

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