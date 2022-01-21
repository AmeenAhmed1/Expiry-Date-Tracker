package com.ameen.expirydatetracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ameen.expirydatetracker.data.ItemModel
import com.ameen.expirydatetracker.repository.ItemRepository
import kotlinx.coroutines.launch

class ItemViewModel(private val itemRepository: ItemRepository) : ViewModel() {

    fun insertItemLocally(item: ItemModel) = viewModelScope.launch {
        itemRepository.insertItemLocally(item)
    }
}