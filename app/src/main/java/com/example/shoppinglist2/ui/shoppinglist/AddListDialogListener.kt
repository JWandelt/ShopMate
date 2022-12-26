package com.example.shoppinglist2.ui.shoppinglist

import com.example.shoppinglist2.data.db.entities.ShoppingItem
import com.example.shoppinglist2.data.db.entities.ShoppingList

interface AddListDialogListener {
    fun onAddButtonClicked(list : ShoppingList)
}