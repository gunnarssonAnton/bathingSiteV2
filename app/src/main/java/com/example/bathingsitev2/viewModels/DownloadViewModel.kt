package com.example.bathingsitev2.viewModels

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModel
import com.example.bathingsitev2.downloaders.BathSiteDownloader
import com.example.bathingsitev2.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DownloadViewModel @Inject constructor(
   private val repository: Repository
):ViewModel() {
   lateinit var context : Context
   fun setContextFromView(context: Context){
      this.context = context
   }
   fun getMyViewClient(): MyWebViewClient {
      return MyWebViewClient()
   }
   fun getDownloadManager(): BathSiteDownloader {
      context.registerReceiver(broadcastReceiver,
         IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
      )
      return BathSiteDownloader(this.context)
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

               println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS"+repository.getAllSites().size)
               println("Download with ID $id finished!")
            }
         }
      }

   }

}