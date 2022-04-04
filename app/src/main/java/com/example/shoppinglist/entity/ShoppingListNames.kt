package com.example.shoppinglist.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "shopping_list_names")
data class ShoppingListNames(
    @PrimaryKey(autoGenerate = true)
    val id:Int?,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "price")
    val price:Int,
    @ColumnInfo(name = "time")
    val time:String,
    @ColumnInfo(name = "allItemCount")
    val countItem:Int,
    @ColumnInfo(name = "checkItemsCounter")
    val checkItemCounter:Int,
    @ColumnInfo(name = "ItemsIds")
    val itemsIds:String
):Serializable
