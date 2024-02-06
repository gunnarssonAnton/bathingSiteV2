package com.example.bathingsitev2.viewModels

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Environment
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bathingsitev2.components.PreferencesManager
import com.example.bathingsitev2.database.BathingSite
import com.example.bathingsitev2.downloaders.BathSiteDownloader
import com.example.bathingsitev2.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class DownloadViewModel @Inject constructor(
   private val repository: Repository
):ViewModel() {
   lateinit var viewModelContext : Context
   fun setContextFromView(context: Context){
      this.viewModelContext = context
   }
   fun getMyViewClient(): MyWebViewClient {
      return MyWebViewClient()
   }

   fun getDownloadManager(): BathSiteDownloader {
      viewModelContext.registerReceiver(broadcastReceiver,
         IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
      )
      return BathSiteDownloader(this.viewModelContext)
   }

   class MyWebViewClient :WebViewClient(){
      override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
         return false
      }
   }
   private val broadcastReceiver = object: BroadcastReceiver(){
      override fun onReceive(context: Context?, intent: Intent?) {
         if(intent?.action == "android.intent.action.DOWNLOAD_COMPLETE") {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L)
            if(id != -1L) {
                  val currentDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS+"/${PreferencesManager(viewModelContext).getData("FILENAME")}").toURI())

                  val file = currentDir.readLines()
                  file.forEach{s: String ->
                     val coords =
                        parser(s)[0]
                        .dropLast(1)
                           .split(",")

                     val name = parser(s)[1]
                        .split(",")[0]


                     val newSite = BathingSite(
                        null,
                        name= name,
                        longitude = coords[0],
                        latitude = coords[1]
                     )
                     insertBathingSite(newSite)
                  }

               println("Download with ID $id finished!")
               currentDir.delete()
            }
         }
      }

      fun parser(csvContent: String):List<String> {
          return csvContent
             .drop(0)
             .drop(1)
             .split("\"\"")
             .dropLast(1)



      }
   }
   fun insertBathingSite(newBathSite: BathingSite){
      viewModelScope.launch {
         if(repository.getAllSites().any { it.latitude.equals(newBathSite.latitude) && it.longitude.equals(newBathSite.longitude) }){
            println("Already exists") //TODO FIXA RUTA
         }else{
            //TODO FIXA RUTA
            repository.insertBathingSite(newBathSite)
         }
      }
   }

   fun Any.println(){
      println(this)
   }
}