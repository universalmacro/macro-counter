package com.example.macrocounter.compositionLocal

import androidx.compose.runtime.compositionLocalOf
import com.example.macrocounter.viewModel.UserViewModel

val LocalUserViewModel =
    compositionLocalOf<UserViewModel> { error("User View Model Context Not Found") }