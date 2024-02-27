package com.example.macrocounter.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.macrocounter.screens.LoginScreen

@Composable
fun CounterApp (){
    Surface (
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ){
        LoginScreen()
    }
}