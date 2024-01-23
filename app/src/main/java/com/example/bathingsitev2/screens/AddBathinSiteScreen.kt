package com.example.bathingsitev2.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bathtub
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.bathingsitev2.components.DatePickerDialog
import com.example.bathingsitev2.components.OutlinedNumberField
import com.example.bathingsitev2.components.RatingBar
import com.example.bathingsitev2.components.SiteDialog
import com.example.bathingsitev2.viewModels.AddBathingSiteViewModel

@Composable
fun AddBathingSiteScreen(
    navController: NavHostController,
    addBathingSiteViewModel: AddBathingSiteViewModel = AddBathingSiteViewModel()
) {

    ActionBar(addBathingSiteViewModel,navController)
    {
        AddBathingSiteForm(addBathingSiteViewModel)
    }
}

@Composable
fun AddBathingSiteForm(viewModel: AddBathingSiteViewModel) {

    Box(modifier = Modifier
        .fillMaxSize()
        ){
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween) {
            OutlinedTextField(
                value = viewModel.name,
                onValueChange = { viewModel.name = it },
                label = { Text("name:") },
                modifier = Modifier.fillMaxWidth(),
                isError = viewModel.showError,
                trailingIcon = {
                    if (viewModel.showError)
                        Icon(imageVector = Icons.Filled.Error, contentDescription = null, tint = MaterialTheme.colors.error)
                }
            )
            if (viewModel.showError){
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
                isError = viewModel.showError,
                trailingIcon = {
                    if (viewModel.showError)
                        Icon(imageVector = Icons.Filled.Error, contentDescription = null, tint = MaterialTheme.colors.error)
                }
            )
            if (viewModel.showError){
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
                    isError = viewModel.showError
                )

                OutlinedNumberField(
                    value = viewModel.latitude,
                    onNumberChange = {viewModel.latitude = it},
                    label = { Text("Latitude:")},
                    modifier = Modifier.weight(1F),
                    isError = viewModel.showError
                )

            }
            if (viewModel.showError){
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
    navigateBack: NavController,
    content: @Composable()()->Unit
) {
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
                    IconButton(onClick = { navigateBack.navigate(Screen.MainScreen.route)}) {
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
                },
            )
                

        },
        content = {
            if(viewModel.showDialog){
                SiteDialog(
                    onDismissRequest = { viewModel.removeDialog() },
                    onConfirmation = { viewModel.removeDialog() },
                    dialogTitle = viewModel.name,
                    dialogText = viewModel.getInfoBathSite(),
                    icon = Icons.Filled.Bathtub
                )
            }
            content()

        }
    )
}
