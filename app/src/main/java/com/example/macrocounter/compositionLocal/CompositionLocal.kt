package com.example.macrocounter.compositionLocal

import androidx.compose.runtime.compositionLocalOf
import com.example.macrocounter.viewModel.FoodViewModel
import com.example.macrocounter.viewModel.SpaceViewModel
import com.example.macrocounter.viewModel.TableViewModel
import com.example.macrocounter.viewModel.UserViewModel

val LocalUserViewModel =
    compositionLocalOf<UserViewModel> { error("User View Model Context Not Found") }


val LocalSpaceViewModel =
    compositionLocalOf<SpaceViewModel> { error("table View Model Context Not Found") }

val LocalTableViewModel =
    compositionLocalOf<TableViewModel> { error("table View Model Context Not Found") }

val LocalFoodViewModel =
    compositionLocalOf<FoodViewModel> { error("table View Model Context Not Found") }