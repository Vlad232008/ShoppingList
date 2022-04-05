package com.example.shoppinglist.db.interfaceDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.shoppinglist.entities.NoteItem
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Query("SELECT* From note_list")
    fun getAllNotes(): Flow<List<NoteItem>>
    @Query("DELETE FROM note_list where id is :id")
    suspend fun deleteNote(id:Int)
    @Insert
    suspend fun insertNote(note: NoteItem)
}