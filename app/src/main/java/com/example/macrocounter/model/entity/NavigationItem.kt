package com.example.macrocounter.model.entity

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * 導航欄對象
 *
 * @property title  導航欄的標題
 * @property icon 導航欄圖標
 */
data class NavigationItem(
    val title: String,
    val icon: ImageVector
)