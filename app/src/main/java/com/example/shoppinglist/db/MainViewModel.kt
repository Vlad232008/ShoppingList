package com.example.shoppinglist.db

import androidx.lifecycle.*
import com.example.shoppinglist.entities.NoteItem
import com.example.shoppinglist.entities.ShoppingListName
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class MainViewModel(database: MainDataBase):ViewModel() {
    val dao = database.getDao()

    val allNotes: LiveData<List<NoteItem>> = dao.getAllNotes().asLiveData()

    val allShopListName: LiveData<List<ShoppingListName>> = dao.getAllShopListName().asLiveData()

    fun insertNote(note:NoteItem) = viewModelScope.launch {
        dao.insertNote(note)
    }
    fun updateNote(note:NoteItem) = viewModelScope.launch {
        dao.updateNote(note)
    }
    fun updateShopListName(name:ShoppingListName) = viewModelScope.launch {
        dao.updateShopListName(name)
    }
    fun deleteNote(id: Int) = viewModelScope.launch {
        dao.deleteNote(id)
    }
    fun deleteShopListName(id: Int) = viewModelScope.launch {
        dao.deleteShopListName(id)
    }
    fun insertShopListName(listName: ShoppingListName) = viewModelScope.launch {
        dao.insertShopListName(listName)
    }

    class MainViewModelFactory(private val database: MainDataBase): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(MainViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(database) as T
            }
            throw IllegalArgumentException("Unknows ViewModelClass")
        }
    }
}