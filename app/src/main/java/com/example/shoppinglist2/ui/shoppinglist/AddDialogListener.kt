package com.example.shoppinglist2.ui.shoppinglist

import com.example.shoppinglist2.data.db.entities.ShoppingItem

interface AddDialogListener {
    fun onAddButtonClicked(item : ShoppingItem)
}