package com.example.bathingsitev2.components

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

    fun saveData(key:String, value:String){
        val editor = sharedPreferences.edit()
        editor.putString(key,value)
        editor.apply()
    }

    fun getData(key: String,defaultValue:String = "https://dt031g.programvaruteknik.nu/bathingsites/weather.php"):String{
        return sharedPreferences.getString(key, defaultValue)?:defaultValue
    }
}