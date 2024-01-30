package com.example.bathingsitev2.viewModels

import android.graphics.BitmapFactory
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bathingsitev2.components.PreferencesManager
import com.example.bathingsitev2.database.BathingSite
import com.example.bathingsitev2.models.CurrentWeather
import com.example.bathingsitev2.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class AddBathingSiteViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var name by mutableStateOf("")
    var description by mutableStateOf("")
    var address by mutableStateOf("")
    var latitude by mutableStateOf<Number?>(null)
    var longitude by mutableStateOf<Number?>(null)
    var rating by mutableStateOf(0)
    var waterTemp by mutableStateOf<Number>(0)
    var dateForTemp by mutableStateOf("")

    lateinit var currentWeather: CurrentWeather
    private set

    lateinit var preferencesManager: PreferencesManager

    var showWeatherDialog: Boolean by mutableStateOf(false)

    var showERRORDialog: Boolean by mutableStateOf(false)
        private set
    var showLoading: Boolean by mutableStateOf(false)
        private set
    var showSiteDialog by mutableStateOf(false)
        private set
    var showErrorText by mutableStateOf(false)
        private set

    fun clearField(){
        this.name = ""
        this.description = ""
        this.address = ""
        this.latitude = null
        this.longitude = null
        this.rating = 0
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
            this.insertToDB()
            this.showErrorText = false
        }

    }

    fun insertToDB(){
        viewModelScope.launch {
            repository.insertBathingSite(
                BathingSite(
                    null,
                    name = name,
                    description = description,
                    address = address,
                    longitude = longitude.toString(),
                    latitude = latitude.toString(),
                    grade = rating.toString(),
                    water_temp = waterTemp.toString(),
                    date_for_temp = dateForTemp
                )
            )
        }

    }

    fun removeDialog(){
        this.showSiteDialog = false
        this.showERRORDialog = false
        this.showWeatherDialog = false
    }

    fun showLoading(){
        this.showLoading = true
    }
    private fun isValidInput(): Boolean {
        if (this.name.isEmpty()){
            this.showErrorText = true
            return false
        }
        else if(this.address.isNotEmpty() && this.latitude.toString().isEmpty()
            || this.address.isNotEmpty() && this.longitude.toString().isEmpty()){
            this.showErrorText = false
            return true
        }
        if(this.address.isEmpty() || this.latitude.toString().isEmpty() || this.longitude.toString().isEmpty()){
            this.showErrorText = true
            return false
        }

        return true
    }

    fun loadWeather(){
        var dataString: String
        if (constructUrl().isNotEmpty()){

            CoroutineScope(Dispatchers.IO).launch {
                showLoading = true
                dataString = URL(constructUrl()).readText()

                showLoading = if (getStatusCode(dataString = dataString) != "404") {


                    currentWeather = CurrentWeather(getImageFromURL(getWeatherData(dataString = dataString)?.get("icon").toString()),
                        getWeatherData(dataString = dataString)?.get("description").toString(),
                        getMainData(dataString = dataString).get("temp").toString())
                    false
                } else {
                    false
                }
                showWeatherDialog = true
            }

        }else{
            showLoading = false
            showERRORDialog = true
        }


    }
    /**
     * used to get weather data
     */
    private fun getWeatherData(dataString:String): JSONObject? {
        val data = JSONObject(dataString)
        return data.getJSONArray("weather")
                    .getJSONObject(0)
    }

    /**
     * used to get the main data
     */
    private fun getMainData(dataString: String): JSONObject {
        val data = JSONObject(dataString)
        return data.getJSONObject("main")
    }

    private fun getStatusCode(dataString: String): String {
        return JSONObject(dataString).getString("cod")
    }

    private fun getImageFromURL(imgCode: String): ImageBitmap {
        val img = URL("https://openweathermap.org/img/w/$imgCode.png").readBytes()
        return BitmapFactory.decodeByteArray(img,0,img.size).asImageBitmap()
    }

    private fun constructUrl(): String {
        val url = preferencesManager.getData("URL_KEY")
        val newAddress = if (address.split(" ").size > 1){
            address.split(" ")[1]
        }else{
            address
        }
        return if (this.longitude == null && newAddress.isNotEmpty() ||
            this.latitude == null && newAddress.isNotEmpty()){
            "$url?q=$newAddress"
        }else if (longitude != null && latitude != null){
            "$url?lat=$latitude&lon=$longitude"
        }else{
            ""
        }
    }

}
