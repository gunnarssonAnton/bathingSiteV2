package com.example.bathingsitev2.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.bathingsitev2.components.*
import com.example.bathingsitev2.viewModels.AddBathingSiteViewModel
import com.example.bathingsitev2.views.BathingSiteView

@Composable
fun AddBathingSiteScreen(
    navController: NavHostController?,
    addBathingSiteViewModel: AddBathingSiteViewModel = viewModel()
) {
    addBathingSiteViewModel.preferencesManager = PreferencesManager(LocalContext.current)
    val config = LocalConfiguration.current
    when(config.orientation){

        Configuration.ORIENTATION_PORTRAIT -> {
            ActionBar(addBathingSiteViewModel, navController)
            {
                AddBathingSiteForm(
                    viewModel = addBathingSiteViewModel,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Configuration.ORIENTATION_LANDSCAPE -> {
            ActionBar(addBathingSiteViewModel, navController)
            {
                Row {
                    BathingSiteView(modifier = Modifier.weight(1F))
                    AddBathingSiteForm(
                        viewModel = addBathingSiteViewModel,
                        modifier = Modifier.weight(1F)
                    )
                }
            }
        }
    }


}

@Composable
fun AddBathingSiteForm(
    viewModel: AddBathingSiteViewModel,
    modifier: Modifier
) {

    Box(modifier = modifier
        ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween) {
            OutlinedTextField(
                value = viewModel.name,
                onValueChange = { viewModel.name = it },
                label = { Text("name:") },
                modifier = Modifier.fillMaxWidth(),
                isError = viewModel.showErrorText,
                trailingIcon = {
                    if (viewModel.showErrorText)
                        Icon(imageVector = Icons.Filled.Error, contentDescription = null, tint = MaterialTheme.colors.error)
                }
            )
            if (viewModel.showErrorText){
                Text(
                    text = "Fill Name",
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = viewModel.description,
                onValueChange = { viewModel.description = it },
                label = {Text("Description:")},
                modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = viewModel.address,
                onValueChange = { viewModel.address = it },
                label = {Text("Address:")},
                modifier = Modifier.fillMaxWidth(),
                isError = viewModel.showErrorText,
                trailingIcon = {
                    if (viewModel.showErrorText)
                        Icon(imageVector = Icons.Filled.Error, contentDescription = null, tint = MaterialTheme.colors.error)
                }
            )
            if (viewModel.showErrorText){
                Text(
                    text = "Fill Address",
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                OutlinedNumberField(
                    value = viewModel.longitude,
                    onNumberChange = {viewModel.longitude = it},
                    label = { Text("Longitude:")},
                    modifier = Modifier.weight(1F),
                    isError = viewModel.showErrorText
                )

                OutlinedNumberField(
                    value = viewModel.latitude,
                    onNumberChange = {viewModel.latitude = it},
                    label = { Text("Latitude:")},
                    modifier = Modifier.weight(1F),
                    isError = viewModel.showErrorText
                )

            }
            if (viewModel.showErrorText){
                Text(
                    text = "Fill ALL",
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            RatingBar(
                currentRating = viewModel.rating,
                onRatingChanged = { viewModel.rating = it }
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                OutlinedNumberField(
                    value = viewModel.waterTemp,
                    label = { Text(text = "Water temp:") },
                    onNumberChange = {viewModel.waterTemp = it},
                    modifier = Modifier.weight(1F),
                    isError = false
                )
                DatePickerDialog(
                    modifier = Modifier
                        .weight(2F)
                        .fillMaxWidth(),
                    pickedDate = {viewModel.dateForTemp = it}
                )
            }

        }
    }
}

@Composable
fun ActionBar(
    viewModel: AddBathingSiteViewModel,
    navController: NavHostController?,
    content: @Composable ()->Unit
) {
    var showDropdown by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.primary,
                title = {
                    Text(
                        text = "Add Bathing place",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController?.navigate(Screen.MainScreen.route) }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "localized description",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    TextButton(onClick = { viewModel.clearField() }) {
                        Text(
                            text = "CLEAR",
                            color = Color.White
                        )
                    }
                    IconButton(onClick = { viewModel.saveField() }) {
                        Icon(
                            imageVector = Icons.Filled.Save,
                            contentDescription = "Button to save",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = { showDropdown=true}) {
                        Icon(imageVector = Icons.Filled.MoreVert, contentDescription = null)
                    }
                    DropdownMenu(
                        expanded = showDropdown,
                        onDismissRequest = { showDropdown = false }
                    ) {
                        DropdownMenuItem(onClick = { viewModel.loadWeather()}) {
                            Text(text = "Show Weather")
                        }
                        DropdownMenuItem(
                            onClick = {
                                navController?.navigate(Screen.SettingsScreen.route)
                            }) {
                            Text(text = "Settings")
                        }
                    }
                },
            )
                

        },
        content = {
            if(viewModel.showSiteDialog){
                SiteDialog(
                    onDismissRequest = { viewModel.removeDialog() },
                    onConfirmation = { viewModel.removeDialog() },
                    dialogTitle = viewModel.name,
                    dialogText = viewModel.getInfoBathSite(),
                    icon = Icons.Filled.Bathtub
                )
            }
            if(viewModel.showLoading){
                LoadingDialog()
            }
            if (viewModel.showWeatherDialog){
                WeatherDialog(
                    imageBitmap = viewModel.currentWeather.imageBitmap,
                    description = viewModel.currentWeather.description,
                    temp = viewModel.currentWeather.temp,
                    onDismissRequest = {viewModel.showWeatherDialog=false}
                )
            }
            if(viewModel.showERRORDialog){
                ErrorDialog(
                    title = "ERROR",
                    message = "FILL ADDRESS OR LATITUDE AND LONGITUDE TO CONTINUE",
                    onDismissRequest = { viewModel.removeDialog() }
                )
            }
            content()

        }
    )
}

@Preview
@Composable
fun AddBathingSiteScreenPrev() {
    AddBathingSiteScreen(navController = null)
}
