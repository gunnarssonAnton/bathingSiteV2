package com.example.bathingsitev2.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.bathingsitev2.R
import com.example.bathingsitev2.viewModels.AllBathingSitesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllBathingSitesScreen(
    navController: NavHostController?,
    viewModel: AllBathingSitesViewModel = hiltViewModel()
) {
    val bathingSites = viewModel.bathingSites.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults
                    .topAppBarColors(containerColor = MaterialTheme.colors.primary),
                title = {
                    Text(
                        text = "All Sites",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController?.navigate(Screen.MainScreen.route)//TODO FIXA EN BÄTTRE
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "navigation back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    TextButton(onClick = { navController?.navigate(Screen.DownloadScreen.route) }) {
                        Text(text = "DOWNLOAD")
                    }
                }
            )
        }
    ) {

        LazyColumn(modifier = Modifier.padding(it)){
            items(bathingSites.value){bathingSite->
                Card(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clickable { println(bathingSite.name) },
                    colors= CardDefaults.cardColors(containerColor = Color(33, 150, 243)),
                    elevation = CardDefaults.cardElevation(10.dp),

                    ) {
                    Row(
                        modifier= Modifier.padding(6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,

                        ) {
                        Image(
                            painter = painterResource(id = R.drawable.image_for_card),
                            contentDescription = "Image"
                        )
                        Column(modifier = Modifier.padding(10.dp)) {
                            bathingSite.name?.let { name -> Text(text = name, fontSize = 20.sp) }
                        }
                    }
                }
            }

        }

    }
}

@Preview
@Composable
fun cardPrev() {

    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        colors= CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(10.dp),
        border = BorderStroke(0.3.dp, Color.Blue),


    ) {
        Row(
            modifier= Modifier.padding(6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,

        ) {
            Image(
                painter = painterResource(id = R.drawable.image_for_card),
                contentDescription = "Image"
            )
            Column(modifier = Modifier.padding(10.dp)) {
                Text(text = "", fontSize = 20.sp)
            }
        }
    }
}