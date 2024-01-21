package com.example.bathingsitev2.views

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bathingsitev2.R

@Composable
fun BathingSiteView(amount: Int) {
    val amountBathingSites = remember { mutableStateOf(amount)}
    Box(contentAlignment = Alignment.Center){
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            BathingSiteImg { amountBathingSites.value++ }
            Text(text = "${amountBathingSites.value} Bathing sites",
            fontSize = 30.sp)

        }
    }
}

@Composable
fun BathingSiteImg(onClick: ()-> Unit) {

    Image(painterResource(id = R.drawable.skylt_bad,),
        "Image Of Sign",
    modifier = Modifier
        .size(500.dp)
        .clickable { onClick() }
    )
}


@Preview
@Composable
fun BatingSiteViewPrev() {
    BathingSiteView(amount = 10)
}