package com.example.macrocounter.screens

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.macrocounter.R
import com.example.macrocounter.compositionLocal.LocalUserViewModel
import kotlinx.coroutines.launch


@Composable
fun SettingScreen(onBack: () -> Unit) {

    val userViewModel = LocalUserViewModel.current
    val coroutineScope = rememberCoroutineScope()
    val showingDialog = remember { mutableStateOf(false) }

    var loading by remember { mutableStateOf(false) }

    if (showingDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showingDialog.value = false
            },

            title = {
                Text(text = "保存成功")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showingDialog.value = false
                    },
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text("確認")
                }
            },
        )
    }


    Column(modifier = Modifier) {

        LazyColumn(modifier = Modifier) {

            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 24.dp, horizontal = 8.dp)
                ) {

                    IconButton(onClick = {
                        coroutineScope.launch {
                            userViewModel.clear()
                            onBack()
                        }
                    }) {
                        Icon(Icons.Filled.ExitToApp, null)
                    }
                    Text(
                        "登出",
                        textAlign = TextAlign.Center,

                    )
                }
            }

        }

        Column (
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            SimpleSettingTextFieldSample(
                value = userViewModel.baseUrl,
                onValueChange = { userViewModel.baseUrl = it })


            Spacer(modifier = Modifier.height(10.dp))

            Button(
                shape = RoundedCornerShape(5.dp),
                enabled = !loading,
                onClick = {
                    loading = true

                    coroutineScope.launch{
                        userViewModel.saveBaseUrl()
                        showingDialog.value = true
                        loading = false

                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.2f)
                    .height(50.dp),
            ) {
                Text(
                    text = "保存"
                )
            }
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .progressSemantics()
                        .size(32.dp)
                )
            }
        }

    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SimpleSettingTextFieldSample(
    value: String,
    onValueChange: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(5.dp),
        label = {
            Text("BASEURL",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelMedium,
            ) },
        placeholder = { Text(text = "BASEURL") },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.primary),
        singleLine = true,
        modifier = Modifier.fillMaxWidth(0.8f),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                // do something here
            }
        )
    )
}
