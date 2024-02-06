package com.example.bathingsitev2.viewModels

import androidx.compose.runtime.mutableStateOf
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
): ViewModel() {
    private val _bathingSites = MutableStateFlow(emptyList<BathingSite>())
    val bathingSites: StateFlow<List<BathingSite>> = _bathingSites
    val selectedSiteId = mutableStateOf(0)
    val showSelectedSite = mutableStateOf(false)
    val showErrorDialog = mutableStateOf(false)
    init {
        viewModelScope.launch {
            _bathingSites.emit(repository.getAllSites())
        }
    }
    fun getSpecificSite(): BathingSite? {

        if(selectedSiteId.value != 0){
            return repository.getSpecificSite(selectedSiteId.value)
        }else{
            this.showErrorDialog.value = true
        }
        return null
    }

    fun getInfoAboutSite():String{
        return "Name: ${getSpecificSite()?.name} \n" +
                "Latitude: ${getSpecificSite()?.latitude} \n" +
                "Longitude: ${getSpecificSite()?.longitude} \n"

    }

}

