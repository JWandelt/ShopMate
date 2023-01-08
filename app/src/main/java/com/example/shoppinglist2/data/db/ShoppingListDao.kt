package com.example.shoppinglist2.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.shoppinglist2.data.db.entities.ListWithItems
import com.example.shoppinglist2.data.db.entities.ShoppingList

@Dao
interface ShoppingListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(list : ShoppingList)

    @Delete
    suspend fun delete(list : ShoppingList)

    @Transaction
    @Query("SELECT * FROM shopping_lists")
    fun getAllLists() : LiveData<List<ShoppingList>>

    @Transaction
    @Query("SELECT * FROM shopping_lists WHERE listID = :listID")
    fun getListWithItems(listID : Int) : List<ListWithItems>

    @Transaction
    @Query("DELETE FROM shopping_items WHERE list_id = :listID")
    suspend fun deleteItemsOnList(listID: Int)
}
