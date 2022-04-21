package com.example.shoppinglist.db

import androidx.lifecycle.*
import com.example.shoppinglist.entities.LibraryItem
import com.example.shoppinglist.entities.NoteItem
import com.example.shoppinglist.entities.ShopListItem
import com.example.shoppinglist.entities.ShopListNameItem
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class MainViewModel(database: MainDataBase):ViewModel() {
    val dao = database.getDao()

    val allNotes: LiveData<List<NoteItem>> = dao.getAllNotes().asLiveData()

    val allShopListNameItem: LiveData<List<ShopListNameItem>> = dao.getAllShopListName().asLiveData()

    fun getAllItemsFromList(listId:Int): LiveData<List<ShopListItem>>{
        return dao.getAllShopListItem(listId).asLiveData()
    }

    fun insertNote(note:NoteItem) = viewModelScope.launch {
        dao.insertNote(note)
    }
    fun updateNote(note:NoteItem) = viewModelScope.launch {
        dao.updateNote(note)
    }
    fun updateListItem(shopItem:ShopListItem) = viewModelScope.launch {
        dao.updateListItem(shopItem)
    }
    fun updateShopListName(nameItem:ShopListNameItem) = viewModelScope.launch {
        dao.updateShopListName(nameItem)
    }
    fun deleteNote(id: Int) = viewModelScope.launch {
        dao.deleteNote(id)
    }
    fun deleteShopListName(id: Int, deleteList: Boolean) = viewModelScope.launch {
        if (deleteList) dao.deleteShopListName(id)
        dao.deleteShopListItem(id)
    }
    fun insertShopListName(listNameItem: ShopListNameItem) = viewModelScope.launch {
        dao.insertShopListName(listNameItem)
    }
    fun insertShopItem(shopList: ShopListItem) = viewModelScope.launch {
        dao.insertItem(shopList)
        if (!isLibraryExists(shopList.name)) dao.insertLibraryItem(LibraryItem(null, shopList.name))
    }
    private suspend fun isLibraryExists(name: String): Boolean {
        return dao.getAllLibraryItem(name).isNotEmpty()
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