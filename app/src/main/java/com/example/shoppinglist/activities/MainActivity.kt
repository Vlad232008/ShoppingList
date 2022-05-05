package com.example.shoppinglist.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ActivityMainBinding
import com.example.shoppinglist.dialogs.NewListDialog
import com.example.shoppinglist.fragment.FragmentManager
import com.example.shoppinglist.fragment.NoteFragment
import com.example.shoppinglist.fragment.ShopListNamesFragment
import com.example.shoppinglist.setting.SettingActivity

class MainActivity : AppCompatActivity(), NewListDialog.Listener {
    lateinit var binding: ActivityMainBinding
    private var currentMenuItemId = R.id.shoplist
    private var currentTheme:String = ""
    private lateinit var defPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        defPref = PreferenceManager.getDefaultSharedPreferences(this)
        setTheme(getSelectedTheme())
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        currentTheme = defPref.getString("theme_key", "red").toString()
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)
        setBottomNavListener()
        title = "Список покупок"
        FragmentManager.setFragment(ShopListNamesFragment.newInstance(), this)
    }

    private fun getSelectedTheme():Int{
        return when {
            defPref.getString("theme_key", "red") == "red" -> {
                R.style.Theme_ShoppingListLightRed
            }
            defPref.getString("theme_key", "blue") == "blue" -> {
                R.style.Theme_ShoppingListLightBlue
            }
            else -> {
                R.style.Theme_ShoppingListSun
            }
        }
    }

    private fun setBottomNavListener(){
        binding.bNav.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.shoplist->{
                    currentMenuItemId = R.id.shoplist
                    title = "Список покупок"
                    FragmentManager.setFragment(ShopListNamesFragment.newInstance(), this)
                }
                R.id.notes->{
                    currentMenuItemId = R.id.notes
                    title = "Заметки"
                    FragmentManager.setFragment(NoteFragment.newInstance(), this)
                }
                R.id.newItem->{
                    FragmentManager.currentFrag?.onClickNew()
                }
                R.id.settings->{
                    startActivity(Intent(this, SettingActivity::class.java))
                }
            }
            true
        }
    }

    override fun onResume() {
        super.onResume()
        binding.bNav.selectedItemId = currentMenuItemId
        if (defPref.getString("theme_key", "red") != currentTheme) recreate()
    }

    override fun onClick(name: String) {
        Log.d("MyLog", "Name: $name")
    }
}