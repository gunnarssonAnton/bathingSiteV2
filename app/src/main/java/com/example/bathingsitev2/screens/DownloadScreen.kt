package com.example.bathingsitev2.screens

import android.webkit.WebView
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.bathingsitev2.components.LoadingDialog
import com.example.bathingsitev2.viewModels.DownloadViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloadScreen(
    navHostController: NavHostController,
    viewModel: DownloadViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        TopAppBar(
            colors = TopAppBarDefaults
                .topAppBarColors(containerColor = MaterialTheme.colors.primary),
            title = {
                Text(
                    text = "Download sites",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 20.sp,
                    color = Color.White
                )
            },
            navigationIcon = {
                IconButton(onClick = {
                    navHostController.navigate(Screen.MainScreen.route)
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "navigation back",
                        tint = Color.White
                    )
                }
            }
        )
    }) {

        viewModel.setContextFromView(context = LocalContext.current.applicationContext)
        AndroidView(
            modifier = Modifier.padding(paddingValues = it),
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
                        viewModel.isLoading.value = true
                        viewModel
                            .getDownloadManager()
                            .downloadFile(url, userAgent, contentDisposition, mimeType, contentLenght)
                    }

                }

            }
        )
        if (viewModel.isLoading.value){
            LoadingDialog(text = "Downloading Sites")
        }
    }
}

