package com.example.bathingsitev2.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun NumberField(
    value: Number,
    label: @Composable() (()->Unit)?,
    onNumberChange : (Number)->Unit,
    modifier: Modifier) {
    TextField(
        value = value.toString() ?:"",
        label = label,
        modifier = modifier,
        onValueChange = {
            it.toDoubleOrNull()?.let { value ->
                if (value % 1.0 == 0.0){
                    onNumberChange(value.toInt())
                }
                else {
                    onNumberChange(value)
                }
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@Composable
fun OutlinedNumberField(
    value: Number?,
    label: @Composable() (()->Unit)?,
    onNumberChange: (Number)->Unit,
    modifier: Modifier,
    isError: Boolean
) {
    OutlinedTextField(
        value = value?.toString() ?:"",
        label = label,
        modifier = modifier,
        isError = isError,
        onValueChange = {
            it.toDoubleOrNull()?.let { value ->
                if (value % 1.0 == 0.0){
                    onNumberChange(value.toFloat())
                }
                else {
                    onNumberChange(value)
                }
            }
        },

        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
    )
}

/*
@Composable
fun RatingBar(
    modifier: Modifier,
    maxRating:Float = 5.0F,
    currentRating: Float,
    onRatingChanged: (Float)->Unit,
    starsColor: Color = Color.Yellow
) {
    val filledStars by remember{ mutableStateOf(floor(currentRating).toInt())}
    val unFilledStars by remember { mutableStateOf(maxRating.toInt() - ceil(currentRating))}
    val halfStar by remember { mutableStateOf(!(currentRating.rem(1).equals(0.0)))}

    Row(modifier = modifier) {
       repeat(filledStars){
           Icon(
               imageVector = Icons.Outlined.Star,
               contentDescription = null,
               tint = starsColor
           )
       }
        if (halfStar){
            Icon(
                imageVector = Icons.Outlined.StarHalf,
                contentDescription = null,
                tint = starsColor
            )
        }
        repeat(unFilledStars.toInt()){
            Icon(
                imageVector = Icons.Outlined.StarOutline,
                contentDescription = null,
                tint = starsColor
            )
        }
    }
}*/
@Composable
fun RatingBar(
    maxRating: Int = 5,
    currentRating: Float,
    onRatingChanged: (Float) -> Unit,
    starsColor: Color = Color.Yellow
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center) {
        for (i in 1..maxRating) {
            Icon(
                imageVector = if (i <= currentRating) Icons.Filled.Star
                else Icons.Filled.StarOutline,
                contentDescription = null,
                tint = if (i <= currentRating) starsColor
                else Color.Unspecified,

                modifier = Modifier
                    .height(60.dp)
                    .size(60.dp)
                    .clickable { onRatingChanged(i.toFloat()) }
                    .padding(4.dp)
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePickerDialog(
    onDateSelected : (String) -> Unit,
    onDismiss : () -> Unit
) {
    val datePickerState = rememberDatePickerState(selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return utcTimeMillis <= System.currentTimeMillis()
        }
    })
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: convertMillisToDate(System.currentTimeMillis())

    DatePickerDialog(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = {
                onDateSelected(selectedDate)
                onDismiss()
            }){
                Text(text = "OK")
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismiss()
            }) {
                Text(text = "Cancel")
            }
        }
    ) {
        DatePicker(
            state = datePickerState
        )
    }
}

private fun convertMillisToDate(millis :Long):String{
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    return formatter.format(Date(millis))
}

@Composable
fun DatePickerDialog(modifier: Modifier, pickedDate:(String)->Unit) {
    var date by remember { mutableStateOf("Open date picker dialog")}

    var showDatePicker by remember { mutableStateOf(false) }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        OutlinedButton(
            onClick = {showDatePicker = true},
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            shape= MaterialTheme.shapes.medium,
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
        ) {
            Text(
                text = date,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
    if (showDatePicker){
        MyDatePickerDialog(
            onDateSelected = {
                date = it;
                pickedDate(it) },
            onDismiss = { showDatePicker = false }
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SiteDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}