package com.example.shoppinglist.setting

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.shoppinglist.R

class SettingFragment: PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting_preference, rootKey)
    }
}