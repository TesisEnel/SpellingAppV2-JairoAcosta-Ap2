package com.example.spellingappv2.ui.Usuario

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatListNumberedRtl
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.twotone.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.spellingappv2.ui.componentes.ValidationText
import com.example.spellingappv2.ui.theme.Blue1
import com.example.spellingappv2.ui.theme.Yellow1
import com.example.spellingappv2.ui.theme.Yellow2
import com.example.spellingappv2.util.Screen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RegistroUsuarioScreen(
    navHostController: NavHostController,
    viewModel: UsuarioViewModel = hiltViewModel()
) {
    var nameError by remember {
        mutableStateOf(false)
    }
    var ageError by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Kid Register",
                        fontSize = 35.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.Cursive
                    )
                },
                backgroundColor = Blue1
            )

        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    nameError = viewModel.nombres.isBlank()
                    ageError = viewModel.edad.isBlank()
                    if (!nameError && !ageError) {
                        if (isNumeric(viewModel.edad) == false) {
                            Toast.makeText(
                                context,
                                "Age isn't correct. Please enter a valid number",
                                Toast.LENGTH_LONG
                            ).show();
                        } else if (viewModel.edad.toFloat() > 0) {
                            viewModel.Guardar()
                            Toast.makeText(context, "The kid had been saving", Toast.LENGTH_LONG)
                                .show()
                            //navHostController.navigateUp()
                            navHostController.navigate(Screen.ScoreScreen.route)
                        } else {
                            Toast.makeText(context, "The age shouldn't be zero", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                },
                backgroundColor = Blue1
            )
            {
                Icon(imageVector = Icons.TwoTone.Save, contentDescription = null)
            }
        }

    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            OutlinedTextField(
                value = viewModel.nombres,
                onValueChange = { viewModel.nombres = it },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "Name",fontFamily = FontFamily.Cursive)
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null
                    )
                },
                isError = nameError,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )
            ValidationText(estado = nameError)

            Spacer(modifier = Modifier.height(40.dp))

            OutlinedTextField(
                value = viewModel.edad,
                onValueChange = { viewModel.edad = it },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "Age", fontFamily = FontFamily.Cursive)
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.FormatListNumberedRtl,
                        contentDescription = null
                    )
                },
                isError = ageError,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )

            ValidationText(estado = ageError)
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

fun isNumeric(a: String): Boolean {
    try {
        a.toInt();
        return true;
    } catch (e: NumberFormatException) {
        return false;
    }
}