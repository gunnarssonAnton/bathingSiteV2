package com.example.bathingsitev2.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bathtub
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bathingsitev2.viewModels.BathingSiteViewModel

@Composable
fun BathingSiteView(
    modifier: Modifier,
    bathingSiteViewModel: BathingSiteViewModel = viewModel()
) {

    Box(
        modifier= modifier,
        contentAlignment = Alignment.Center){
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            BathingSiteImg { bathingSiteViewModel.increaseAmount() }
            Text(text = "${bathingSiteViewModel.amount.value} Bathing sites",
            fontSize = 30.sp)

        }
    }
}

@Composable
fun BathingSiteImg(onClick: ()-> Unit) {

    Image(imageVector = Icons.Filled.Bathtub,
        "Image Of Sign",
    modifier = Modifier
        .size(250.dp)
        .clickable { onClick() }
    )
}


@Preview
@Composable
fun BatingSiteViewPrev() {
    BathingSiteView(Modifier)
}