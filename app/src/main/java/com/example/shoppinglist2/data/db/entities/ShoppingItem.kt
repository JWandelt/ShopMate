package com.example.shoppinglist2.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_items")
data class ShoppingItem(
    @ColumnInfo(name = "list_id")
    var listID : Int,
    @ColumnInfo(name = "item_name")
    var name : String,
    @ColumnInfo(name = "item_amount")
    var amount : Int,
    @ColumnInfo(name = "is_bought")
    var bought : Boolean)
{
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null
}