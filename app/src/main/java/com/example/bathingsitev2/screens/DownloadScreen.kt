package com.example.bathingsitev2.screens

import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bathingsitev2.viewModels.DownloadViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun DownloadScreen(
    viewModel: DownloadViewModel = hiltViewModel()
) {
    viewModel.setContextFromView(context = LocalContext.current.applicationContext)
    AndroidView(
        factory = {context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                webViewClient = viewModel.getMyViewClient()

                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.setSupportZoom(true)
            }
        }, update = {webView ->
            webView.loadUrl("https://dt031g.programvaruteknik.nu/bathingsites/download/")
            webView.setDownloadListener { url: String, userAgent: String, contentDisposition: String, mimeType: String, contentLenght: Long ->
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel
                        .getDownloadManager()
                        .downloadFile(url, userAgent, contentDisposition, mimeType, contentLenght)

                }

            }
        }
    )
}



@Preview
@Composable
fun prevDownloadScreen() {
    DownloadScreen()
}