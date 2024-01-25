package com.example.bathingsitev2.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ErrorDialog(
    title: String,
    message : String,
    backgroundColor:Color = Color(0xFFCCCCCC),
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismissRequest() }) {
        Box(
            modifier = Modifier
                .clip(RectangleShape)
                .fillMaxWidth()
                .background(backgroundColor)
        ){
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Blue)
                        .padding(start = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        color= Color.White,
                        fontFamily = FontFamily.Monospace)
                    Surface(
                        onClick = { onDismissRequest() },
                        shape = RectangleShape,
                        modifier = Modifier.padding(2.dp),
                        color = backgroundColor
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close"
                        )
                    }

                }
                Row(modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 20.dp
                ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = "Error",
                        tint = Color.Red,
                        modifier = Modifier.size(48.dp)
                    )
                    Text(text = message, fontFamily = FontFamily.Monospace)
                }
                Surface(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = { onDismissRequest() },
                    shape = RectangleShape,
                    color = backgroundColor,
                    border = BorderStroke(Dp.Hairline,Color.Black)
                    
                ) {
                    Text(
                        text = "OK",
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier
                            .widthIn(120.dp)
                            .padding(vertical = 8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

/*
@Preview
@Composable
fun errorDialogPrev() {
    ErrorDialog(
        title = "ERROR",
        message = "THIS IS AN ERROR MESSAGE",
        onDismissRequest = { println("DISSMISS")}
    )
}*/
