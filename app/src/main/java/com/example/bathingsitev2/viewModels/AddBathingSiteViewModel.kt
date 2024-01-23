package com.example.bathingsitev2.viewModels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

class AddBathingSiteViewModel : ViewModel() {
    var name by mutableStateOf("")
    var description by mutableStateOf("")
    var address by mutableStateOf("")
    var latitude by mutableStateOf<Number?>(null)
    var longitude by mutableStateOf<Number?>(null)
    var rating by mutableStateOf(0F)
    var waterTemp by mutableStateOf<Number>(0)
    var dateForTemp by mutableStateOf("")
    var showDialog by mutableStateOf(false)
        private set
    var showError by mutableStateOf(false)
        private set

    fun clearField(){
        this.name = ""
        this.description = ""
        this.address = ""
        this.latitude = 0
        this.longitude = 0
        this.rating = 0F
        this.waterTemp = 0
        this.dateForTemp = ""
    }

    fun getInfoBathSite():String{
        return "Name: ${this.name} \n" +
                "Description: ${this.description} \n" +
                "Address: ${this.address} \n" +
                "Latitude: ${this.latitude} \n" +
                "Longitude: ${this.longitude} \n" +
                "Rating: ${this.rating} \n" +
                "Water Temp: ${this.waterTemp} \n" +
                "Date: ${this.dateForTemp}"
    }

    fun saveField(){
        if (isValidInput()){
            this.showDialog = true
            this.showError = false
        }

    }
    fun removeDialog(){
        this.showDialog = false
    }
    private fun isValidInput(): Boolean {
        if (this.name.isEmpty()){
            this.showError = true
            return false
        }
        else if(this.address.isNotEmpty() && this.latitude.toString().isEmpty()
            || this.address.isNotEmpty() && this.longitude.toString().isEmpty()){
            this.showError = false
            return true
        }
        if(this.address.isEmpty() || this.latitude.toString().isEmpty() || this.longitude.toString().isEmpty()){
            this.showError = true
            return false
        }

        return true
    }


}
