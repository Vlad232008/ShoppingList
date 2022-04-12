package com.example.shoppinglist.db.interfaceDB

import androidx.room.*
import androidx.room.Dao
import com.example.shoppinglist.entities.NoteItem
import com.example.shoppinglist.entities.ShoppingListName
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Query("SELECT* From note_list")
    fun getAllNotes(): Flow<List<NoteItem>>

    @Query("DELETE FROM note_list where id is :id")
    suspend fun deleteNote(id:Int)

    @Insert
    suspend fun insertNote(note: NoteItem)

    @Update
    suspend fun updateNote(note: NoteItem)

    @Query("SELECT* From shopping_list_names")
    fun getAllShopListName(): Flow<List<ShoppingListName>>

    @Insert
    suspend fun insertShopListName(name: ShoppingListName)

    @Query("DELETE FROM shopping_list_names where id is :id")
    suspend fun deleteShopListName(id:Int)

    @Update
    suspend fun updateShopListName(name: ShoppingListName)

}