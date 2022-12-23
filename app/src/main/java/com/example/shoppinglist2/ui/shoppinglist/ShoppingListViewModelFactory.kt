package com.example.shoppinglist2.ui.shoppinglist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist2.data.repositories.ShoppingListRepository

class ShoppingListViewModelFactory(
    private val repository: ShoppingListRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass : Class<T>) : T {
        return ShoppingListViewModel(repository) as T
    }
}