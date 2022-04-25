package com.example.shoppinglist.setting

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.preference.PreferenceManager
import com.example.shoppinglist.R

class SettingActivity : AppCompatActivity() {
    private lateinit var defPref:SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        defPref = PreferenceManager.getDefaultSharedPreferences(this)
        setTheme(getSelectedTheme())
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        if (savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.placeHolder, SettingFragment()).commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun getSelectedTheme(): Int{
        return if(defPref.getString("theme_key", "red")=="red"){
            R.style.Theme_ShoppingListLightRed
        } else {
            R.style.Theme_ShoppingListLightBlue
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}