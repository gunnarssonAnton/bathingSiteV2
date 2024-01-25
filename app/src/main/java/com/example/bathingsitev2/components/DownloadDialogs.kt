package com.example.bathingsitev2.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun DownloadDialog() {

}

@Composable
fun WeatherDialog(
    imageBitmap: ImageBitmap,
    description: String,
    temp: String,
    onDismissRequest: ()-> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismissRequest()},
        title = { Text(text = "Current weather", fontSize = 20.sp, fontWeight = FontWeight.Bold)},
        text = {
                Row(horizontalArrangement = Arrangement.SpaceBetween){
                    Image(
                        bitmap = imageBitmap,
                        contentDescription = "Weather Image",
                        modifier = Modifier
                                    .size(60.dp)
                                    .weight(1F)
                    )
                    Column(modifier = Modifier.weight(1F)) {
                        Text(text = description, fontSize = 16.sp)
                        Text(text = "$temp\u2103", fontSize = 18.sp)
                    }
                }
            },
        confirmButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(text = "OK")
            }
        })
}

@Composable
fun LoadingDialog() {
    Dialog(
        onDismissRequest = { println("DISSMISS")},
        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Box(
            modifier = Modifier
                .height(100.dp)
                .width(300.dp)
                .background(color = Color.White, shape = RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Row {

                CircularProgressIndicator(
                    modifier = Modifier
                        .size(50.dp),
                    color = MaterialTheme.colors.secondary,
                    trackColor = MaterialTheme.colors.surface
                )
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = "Getting currant weather...",
                    fontSize = 20.sp
                )
            }
        }

    }

}