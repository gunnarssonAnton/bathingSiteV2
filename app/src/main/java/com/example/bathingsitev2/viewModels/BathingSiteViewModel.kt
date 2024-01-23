package com.example.bathingsitev2.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BathingSiteViewModel : ViewModel() {
    val amount = mutableStateOf(0)

    fun increaseAmount(){
        amount.value += 1
        println(this.amount.value)
    }
}