package com.example.bathingsitev2.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bathingsitev2.database.BathingSite
import com.example.bathingsitev2.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllBathingSitesViewModel @Inject constructor(
    private val repository: Repository

):ViewModel() {
    private val _bathingSites = MutableStateFlow(emptyList<BathingSite>())
    val bathingSites: StateFlow<List<BathingSite>> = _bathingSites

    init {
        viewModelScope.launch {
            _bathingSites.emit(repository.getAllSites())
        }
    }

    fun insertToDb(){
        viewModelScope.launch {
            repository.insertBathingSite(
                    BathingSite(
                        null,
                        "NAMN",
                        "en Bad PLATS",
                        "Ostersund",
                        "60.12",
                        "14.22",
                        "3.0",
                        "3",
                        "2023/10/10"
                    )
                )
            _bathingSites.emit(repository.getAllSites())
        }
    }
}

