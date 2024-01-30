package com.example.bathingsitev2.screens

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
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

                }
            )
        }
    ) {
        Column {
            Button(onClick = { viewModel.insertToDb() }, modifier= Modifier.padding(it)) {
                Text("Click me")
            }
            LazyColumn(modifier = Modifier.padding(it)){
                items(bathingSites.value){bathingSites->
                    Card(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(text = "NAME: ${bathingSites.name}")
                            Text(text = "des: ${bathingSites.description}")
                        }
                    }
                }
            }
        }

    }
}