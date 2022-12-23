package com.example.shoppinglist2.data.db.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.example.shoppinglist2.data.db.entities.ShoppingItem
import com.example.shoppinglist2.data.db.entities.ShoppingList

data class ListWithItems(
    @Embedded val list : ShoppingList,
    @Relation(
        parentColumn = "listID",
        entityColumn = "list_id"
    )
    val items : List<ShoppingItem>
)