package com.example.bathingsitev2.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.bathingsitev2.components.PreferencesManager
import com.example.bathingsitev2.viewModels.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = viewModel(),
    navHostController: NavHostController?
) {
    viewModel.preferencesManager = PreferencesManager(LocalContext.current)
    viewModel.editUrl(viewModel.getPreferenceManager().getData("URL_KEY"))

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults
                    .topAppBarColors(containerColor = MaterialTheme.colors.primary),
                title = {
                    Text(
                        text = "Settings",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navHostController?.navigate(Screen.AddBathingSiteScreen.route)
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
        },
        content = {paddingValue->
            if (viewModel.showEditUrl){
                EditInfoDialog(
                    viewModel = viewModel
                )
            }
            ContentView(
                paddingValue,
                showEditUrl= {viewModel.showEditUrl()},
                url= viewModel.url
            )
        }
    )}

@Composable
fun ContentView(
    paddingValues: PaddingValues,
    showEditUrl:()->Unit,
    url:String
) {
    Box(modifier = Modifier
        .padding(paddingValues)
        .fillMaxSize()){
        Column {
            ApiInfoCard(url = url) {
                showEditUrl()
            }
        }

    }

}

@Composable
fun ApiInfoCard(url:String, onCardClick:()->Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = Color.White)
            .clickable(onClick = onCardClick),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier= Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,

        ) {
            Icon(
                imageVector = Icons.Filled.WbSunny,
                contentDescription = "sun",
                modifier = Modifier.padding(end = 20.dp),
                tint = Color.DarkGray
            )
            Column {
                Text(
                    text = "Fetch weather data from",
                    fontSize = 18.sp
                )
                Text(
                    text = url,
                    color = Color.Gray,
                    fontSize = 15.sp
                )
            }
        }

    }
}


@Composable
fun EditInfoDialog(
  viewModel: SettingsViewModel
) {
    var tempUrl by remember { mutableStateOf(viewModel.url) }
    Dialog(onDismissRequest = { viewModel.removeEditUrl() }) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(BorderStroke(3.dp, color = Color.Black))
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = "Edit Info:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                OutlinedTextField(
                    modifier= Modifier
                        .fillMaxWidth(),
                    value = tempUrl,
                    onValueChange = { tempUrl = it}
                )
                OutlinedButton(
                    onClick = {
                                viewModel.getPreferenceManager().saveData("URL_KEY", tempUrl)
                                viewModel.editUrl(
                                    viewModel.getPreferenceManager().getData("URL_KEY")
                                )
                                viewModel.removeEditUrl()
                              },
                    modifier = Modifier
                        .align(Alignment.End),
                    border = BorderStroke(2.dp,Color.Black)
                ) {
                    Text(
                        text = "OK",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun SettingsPrev() {
    SettingsScreen(navHostController = null)
}