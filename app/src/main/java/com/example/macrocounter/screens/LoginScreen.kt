package com.example.macrocounter.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.macrocounter.components.NormalTextComponent
import com.example.macrocounter.R
import com.example.macrocounter.components.CommonTextField
import com.example.macrocounter.components.HeadingTextComponent
import com.example.macrocounter.components.PasswordTextField
import kotlinx.coroutines.launch
import com.example.macrocounter.compositionLocal.LocalUserViewModel
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import com.example.macrocounter.viewModel.UserViewModel
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun LoginScreen(onClose: () -> Unit){
    Surface (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {

        val showingDialog = remember { mutableStateOf(false) }

        val passwordVisible = remember {
            mutableStateOf(false)
        }

        val userViewModel = LocalUserViewModel.current
        val coroutineScope = rememberCoroutineScope()


        if (showingDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    showingDialog.value = false
                },

                title = {
                    Text(text = "${userViewModel.error}")
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


        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NormalTextComponent(value = stringResource(id = R.string.app_name))
            HeadingTextComponent(value = stringResource(id = R.string.login))
            Spacer(modifier = Modifier.height(30.dp))
//            CommonTextField(labelValue = stringResource(id = R.string.username), painterResource = painterResource(
//                id = R.drawable.ic_launcher_foreground
//            ))
            SimpleOutlinedTextFieldSample(
                value = userViewModel.account,
                onValueChange = { userViewModel.account = it })
            Spacer(modifier = Modifier.height(20.dp))
            SimpleOutlinedPasswordTextField(
                value = userViewModel.password,
                onValueChange = { userViewModel.password = it })
//            PasswordTextField(labelValue = stringResource(id = R.string.passord), painterResource = painterResource(
//                id = R.drawable.ic_launcher_foreground
//            ))
            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    coroutineScope.launch{

                        userViewModel.login(onClose = onClose)

                        if(userViewModel.error != ""){
                            Log.d("=====Logingingin", "${userViewModel.error} ")
                            showingDialog.value = true
                        }


                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(50.dp),
            ) {
                Text(
                    text = "登錄"
                )
            }
        }
//    }
    }
}

//
//@Preview
//@Composable
//fun LoginScreenPreview(){
//    LoginScreen()
//}



@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SimpleOutlinedTextFieldSample(
    value: String,
    onValueChange: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var text by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(topEnd =12.dp, bottomStart =12.dp),
        label = {
            Text("用戶名",
//                color = MaterialTheme.colorScheme.primary,
//                style = MaterialTheme.typography.labelMedium,
            ) },
        placeholder = { Text(text = "用戶名或郵箱") },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Email
        ),
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

//password
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SimpleOutlinedPasswordTextField(
    value: String,
    onValueChange: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var password by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(topEnd =12.dp, bottomStart =12.dp),
        label = {
            Text("密碼",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelMedium,
            ) },
        visualTransformation =
        if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
        //  keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.primary),
        trailingIcon = {
            IconButton(onClick = { passwordHidden = !passwordHidden }) {
                val visibilityIcon =
                    if (passwordHidden) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                // Please provide localized description for accessibility services
                val description = if (passwordHidden) "Show password" else "Hide password"
                Icon(imageVector = visibilityIcon, contentDescription = description)
            }
        },
        modifier = Modifier.fillMaxWidth(0.8f),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                // do something here
            }
        )
    )
}