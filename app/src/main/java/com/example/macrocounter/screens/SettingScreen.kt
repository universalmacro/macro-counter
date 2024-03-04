package com.example.macrocounter.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
    }
}

data class MenuItem(@DrawableRes val icon: Int, val title: String)

