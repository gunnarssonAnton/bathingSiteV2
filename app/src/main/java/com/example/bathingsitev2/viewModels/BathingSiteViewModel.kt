package com.example.bathingsitev2.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class BathingSiteViewModel : ViewModel() {
    val amount = mutableStateOf(0)


    fun increaseAmount(){
        println(this.amount.value)
    }
}