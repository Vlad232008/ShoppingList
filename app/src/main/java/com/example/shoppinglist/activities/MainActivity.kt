package com.example.shoppinglist.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ActivityMainBinding
import com.example.shoppinglist.dialogs.NewListDialog
import com.example.shoppinglist.fragment.FragmentManager
import com.example.shoppinglist.fragment.NoteFragment
import com.example.shoppinglist.fragment.ShopListNamesFragment

class MainActivity : AppCompatActivity(), NewListDialog.Listener {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)
        setBottomNavListener()
        title = "Список покупок"
        FragmentManager.setFragment(ShopListNamesFragment.newInstance(), this)
    }
    private fun setBottomNavListener(){
        binding.bNav.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.shoplist->{
                    title = "Список покупок"
                    FragmentManager.setFragment(ShopListNamesFragment.newInstance(), this)
                }
                R.id.notes->{
                    title = "Заметки"
                    FragmentManager.setFragment(NoteFragment.newInstance(), this)
                }
                R.id.newItem->{
                    FragmentManager.currentFrag?.onClickNew()
                }
                R.id.settings->{
                Log.d("MyLog","Settings")
                }
            }
            true
        }
    }

    override fun onClick(name: String) {
        Log.d("MyLog", "Name: $name")
    }
}