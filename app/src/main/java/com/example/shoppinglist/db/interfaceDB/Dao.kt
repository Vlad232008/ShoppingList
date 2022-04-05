package com.example.shoppinglist.db.interfaceDB

import androidx.room.*
import androidx.room.Dao
import com.example.shoppinglist.entities.NoteItem
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Query("SELECT* From note_list")
    fun getAllNotes(): Flow<List<NoteItem>>
    @Query("DELETE FROM note_list where id is :id")
    suspend fun deleteNote(id:Int)
    /*@Query("UPDATE FROM note_list where id is :id")
    suspend fun updateNote(id:Int)*/
    @Insert
    suspend fun insertNote(note: NoteItem)
    @Update
    suspend fun updateNote(note: NoteItem)
}