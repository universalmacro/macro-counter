package com.example.macrocounter.navigation

sealed class Destinations(val route: String) {
    // 首頁；選擇Space
    object HomeFrame : Destinations("HomeFrame")

    // 登錄頁
    object Login : Destinations("Login")

    // 選桌頁面
    object SelectTableFrame : Destinations("SelectTableFrame")
}