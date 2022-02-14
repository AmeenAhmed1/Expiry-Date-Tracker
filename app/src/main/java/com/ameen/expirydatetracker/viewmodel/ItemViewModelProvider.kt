package com.ameen.expirydatetracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ameen.expirydatetracker.repository.ItemRepository

class ItemViewModelProvider(val _itemRepository: ItemRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ItemViewModel(_itemRepository) as T
    }
}