package com.example.shoppinglist.entities

import androidx.room.TypeConverter

class Converter{
    private val separator = ","
    @TypeConverter
    fun getDBValue(model: MutableList<String>?): String =
        if (model == null || model.isEmpty())
            ""
        else
            model.joinToString(separator = separator) { it }
    @TypeConverter
    fun getModelValue(data: String?): MutableList<String> {
        return data?.split(separator)?.toMutableList() ?: mutableListOf()
    }
}