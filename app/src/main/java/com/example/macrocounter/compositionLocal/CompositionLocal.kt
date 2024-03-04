package com.example.macrocounter.compositionLocal

import androidx.compose.runtime.compositionLocalOf
import com.example.macrocounter.viewModel.TableViewModel
import com.example.macrocounter.viewModel.UserViewModel

val LocalUserViewModel =
    compositionLocalOf<UserViewModel> { error("User View Model Context Not Found") }


val LocalTableViewModel =
    compositionLocalOf<TableViewModel> { error("table View Model Context Not Found") }