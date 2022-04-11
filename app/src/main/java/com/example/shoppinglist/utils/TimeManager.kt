package com.example.shoppinglist.utils

import java.text.SimpleDateFormat
import java.util.*

object TimeManager {
    fun getCurrentTime(): String {
        val formatter = SimpleDateFormat("hh:mm:ss - dd/MM/yy", Locale.getDefault())
        return formatter.format(Calendar.getInstance().time)
    }
}