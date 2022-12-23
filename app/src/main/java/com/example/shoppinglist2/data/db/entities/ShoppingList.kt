package com.example.shoppinglist2.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_lists")
data class ShoppingList(
    @ColumnInfo(name = "list_name")
    var name : String) {

    @PrimaryKey(autoGenerate = true)
    var listID : Int? = null
}