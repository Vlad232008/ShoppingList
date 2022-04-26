package com.example.shoppinglist.entities

import android.net.Uri
import androidx.room.*
import java.io.Serializable

@Entity(tableName = "note_list")
data class NoteItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "time")
    val time: String,
    @ColumnInfo(name = "category")
    val category: String,
    @ColumnInfo(name = "uri_image")
    val arrayImage: MutableList<String>
):Serializable
