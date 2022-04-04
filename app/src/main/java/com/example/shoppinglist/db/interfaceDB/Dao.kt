package com.example.shoppinglist.db.interfaceDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.shoppinglist.entity.NoteItem
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Query("SELECT* From note_list")
    fun getAllNotes(): Flow<List<NoteItem>>
    @Insert
    suspend fun insertNote(note: NoteItem)
}