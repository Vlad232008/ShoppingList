package com.example.shoppinglist.db.interfaceDB

import androidx.room.*
import androidx.room.Dao
import com.example.shoppinglist.entities.NoteItem
import com.example.shoppinglist.entities.ShopListItem
import com.example.shoppinglist.entities.ShopListNameItem
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Query("SELECT* From note_list")
    fun getAllNotes(): Flow<List<NoteItem>>

    @Query("SELECT* From shopping_list_names")
    fun getAllShopListName(): Flow<List<ShopListNameItem>>

    @Query("SELECT* From shop_list_item where listId like :listId")
    fun getAllShopListItem(listId: Int): Flow<List<ShopListItem>>

    @Query("DELETE FROM note_list where id is :id")
    suspend fun deleteNote(id:Int)

    @Query("DELETE FROM shopping_list_names where id is :id")
    suspend fun deleteShopListName(id:Int)

    @Query("DELETE FROM shop_list_item where listId is :id")
    suspend fun deleteShopListItem(id:Int)

    @Insert
    suspend fun insertNote(note: NoteItem)

    @Insert
    suspend fun insertItem(shopItem: ShopListItem)

    @Update
    suspend fun updateNote(note: NoteItem)

    @Insert
    suspend fun insertShopListName(nameItem: ShopListNameItem)

    @Update
    suspend fun updateShopListName(nameItem: ShopListNameItem)
}