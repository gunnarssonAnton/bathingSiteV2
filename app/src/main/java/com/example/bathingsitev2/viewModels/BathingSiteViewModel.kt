package com.example.bathingsitev2.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bathingsitev2.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BathingSiteViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    val amount = mutableStateOf(0)
    init {
        viewModelScope.launch {
            amount.value = repository.getAllSites().size
        }
    }

    fun increaseAmount(){
        println(this.amount.value)
    }
}