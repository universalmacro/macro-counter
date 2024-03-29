package com.example.macrocounter.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsPadding
import com.example.macrocounter.model.entity.NavigationItem


@Composable
fun MainFrame(
    onNavigateToSpaceZone: (id: String) -> Unit = {},
    onBack: () -> Unit = {}
) {

    val navigationItems = listOf(
        NavigationItem(title = "空間", icon = Icons.Filled.Home),
        NavigationItem(title = "設置", icon = Icons.Filled.Person),
    )

    var currentNavigationIndex by remember {
        mutableStateOf(0)
    }

    Scaffold(bottomBar = {
        BottomNavigation(
            backgroundColor = MaterialTheme.colors.surface,
            modifier = Modifier.navigationBarsPadding(bottom = true)
        ) {
            navigationItems.forEachIndexed { index, navigationItem ->
                BottomNavigationItem(
                    selected = currentNavigationIndex == index,
                    onClick = {
                        currentNavigationIndex = index
                    },
                    icon = {
                        Icon(
                            imageVector = navigationItem.icon,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(text = navigationItem.title)
                    },
                    selectedContentColor = Color(0xFF422AFB),
                    unselectedContentColor = Color(0xFF999999)
                )
            }
        }
    }
    ) {
        Box(modifier = Modifier.padding(it)) {
            when (currentNavigationIndex) {
                0 -> SpaceListScreen(
                    onBack = onBack,
                    onNavigateToSpaceZone = onNavigateToSpaceZone,
                )
                1 -> SettingScreen(onBack= onBack)
            }
        }
    }


}

@Preview
@Composable
fun MainFramePreview() {
    MainFrame()
}
