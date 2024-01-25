package com.example.bathingsitev2.viewModels

import android.graphics.BitmapFactory
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import com.example.bathingsitev2.models.CurrentWeather
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

class AddBathingSiteViewModel : ViewModel() {
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

    var showWeatherDialog: Boolean by mutableStateOf(false)

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
        this.latitude = 0
        this.longitude = 0
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
            this.showSiteDialog = true
            this.showErrorText = false
        }

    }
    fun removeDialog(){
        this.showSiteDialog = false
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
        CoroutineScope(Dispatchers.IO).launch {
            showLoading = true
                dataString =   URL("https://dt031g.programvaruteknik.nu/bathingsites/weather.php?q=stockholm").readText()

            showLoading = if (getStatusCode(dataString = dataString) != "404") {


                currentWeather = CurrentWeather(getImageFromURL(getWeatherData(dataString = dataString)?.get("icon").toString()),
                    getWeatherData(dataString = dataString)?.get("description").toString(),
                    getMainData(dataString = dataString).get("temp").toString())


                println(
                    """
                            Description: ${getWeatherData(dataString)?.get("description")}
                            Temp: ${getMainData(dataString).get("temp")}
                        """.trimIndent()
                )
                false
            } else {
                false
            }
            showWeatherDialog = true
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

    fun constructUrl(extension: String){

    }


 /*   *//**
     * used to get imgCode from a url and return a bitmap
     *//*
    private fun getImg(imgCode: String): Bitmap {
        val image = URL(getString(R.string.imgUrl)+imgCode+getString(R.string.png)).readBytes()
        return BitmapFactory.decodeByteArray(image, 0, image.size)
    }*/
}
