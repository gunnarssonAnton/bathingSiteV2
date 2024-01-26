package com.example.bathingsitev2.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.bathingsitev2.components.PreferencesManager

class SettingsViewModel : ViewModel() {
    lateinit var preferencesManager: PreferencesManager

    var url by mutableStateOf("")
        private set

    var showEditUrl by mutableStateOf(false )
        private set

    fun showEditUrl(){
        this.showEditUrl = true
    }
    fun editUrl(newUrl: String){
        this.url = newUrl
    }
    fun removeEditUrl(){
        this.showEditUrl = false
    }
    fun getPreferenceManager(): PreferencesManager = preferencesManager
}