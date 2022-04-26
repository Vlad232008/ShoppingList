package com.example.shoppinglist.db.interfaceDB

import androidx.room.*
import androidx.room.Dao
import com.example.shoppinglist.entities.LibraryItem
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

    @Query("SELECT* From library where name like :name")
    suspend fun getAllLibraryItems(name: String): List<LibraryItem>

    @Query("DELETE FROM note_list where id is :id")
    suspend fun deleteNote(id:Int)

    @Query("DELETE FROM shopping_list_names where id is :id")
    suspend fun deleteShopListName(id:Int)

    @Query("DELETE FROM shop_list_item where listId LIKE :listId")
    suspend fun deleteShopListItem(listId:Int)

    @Query("DELETE FROM library where id is :id")
    suspend fun deleteLibraryItem(id:Int)

    @Insert
    suspend fun insertNote(note: NoteItem)

    @Insert
    suspend fun insertItem(shopItem: ShopListItem)

    @Insert
    suspend fun insertLibraryItem(libraryItem: LibraryItem)

    @Update
    suspend fun updateNote(note: NoteItem)

    @Update
    suspend fun updateListItem(shopItem: ShopListItem)

    @Update
    suspend fun updateLibraryItem(libraryItem: LibraryItem)

    @Insert
    suspend fun insertShopListName(nameItem: ShopListNameItem)

    @Update
    suspend fun updateShopListName(nameItem: ShopListNameItem)
}

