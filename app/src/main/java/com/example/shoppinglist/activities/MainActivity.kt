package com.example.shoppinglist.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
        setContentView(binding.root)
        setBottomNavListener()
        FragmentManager.setFragment(ShopListNamesFragment.newInstance(), this)
    }
    private fun setBottomNavListener(){
        binding.bNav.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.shoplist->{
                    FragmentManager.setFragment(ShopListNamesFragment.newInstance(), this)
                }
                R.id.notes->{
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
        Log.d("MyLog","$name")
    }
}