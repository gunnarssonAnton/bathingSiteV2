package com.example.bathingsitev2.downloaders

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import android.webkit.URLUtil
import androidx.core.net.toUri
import com.example.bathingsitev2.components.PreferencesManager

class BathSiteDownloader(
    private val context: Context
) :Downloader{
    private val downloadManager = context.getSystemService(DownloadManager::class.java)
   var filename =""
    private set
    override fun downloadFile(url: String,
                              userAgent: String,
                              contentDisposition: String,
                              mimeType: String,
                              contentLength: Long): Long {

        filename = URLUtil.guessFileName(url,contentDisposition,mimeType)
        val request = DownloadManager
            .Request(url.toUri())
            .setMimeType(mimeType)
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle(filename)
            .addRequestHeader("Authorization","Bearer <token>")
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,filename)
        PreferencesManager(context).saveData("FILENAME",filename)
        return downloadManager.enqueue(request)
    }

}