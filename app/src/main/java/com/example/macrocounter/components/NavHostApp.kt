package com.example.macrocounter.components


import android.util.Log
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.macrocounter.compositionLocal.LocalFoodViewModel
import com.example.macrocounter.compositionLocal.LocalSpaceViewModel
import com.example.macrocounter.compositionLocal.LocalTableViewModel
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.example.macrocounter.compositionLocal.LocalUserViewModel
import com.example.macrocounter.navigation.Destinations
import com.example.macrocounter.screens.LoginScreen
import com.example.macrocounter.screens.MainFrame
import com.example.macrocounter.screens.OrderScreen
import com.example.macrocounter.screens.SelectTableScreen
import com.example.macrocounter.viewModel.AttributeViewModel
import com.example.macrocounter.viewModel.FoodViewModel
import com.example.macrocounter.viewModel.SpaceViewModel
import com.example.macrocounter.viewModel.TableViewModel
import com.example.macrocounter.viewModel.UserViewModel


/**
 * 导航控制器
 *
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavHostApp() {

    val navController = rememberAnimatedNavController()
    ProvideWindowInsets {

        CompositionLocalProvider(
            LocalUserViewModel provides UserViewModel(LocalContext.current),
            LocalSpaceViewModel provides SpaceViewModel(LocalContext.current),
            LocalTableViewModel provides TableViewModel(LocalContext.current),
            LocalFoodViewModel provides FoodViewModel(LocalContext.current)
        ) {

            val userViewModel = LocalUserViewModel.current

            AnimatedNavHost(
                navController = navController,
                startDestination = Destinations.Login.route
            ) {

                composable(
                    Destinations.Login.route,
                    enterTransition = {
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Right
                        )
                    },
                    exitTransition = {
                        slideOutOfContainer(
                            AnimatedContentScope.SlideDirection.Left
                        )
                    },
                ) {
                    if (userViewModel.logged) {
                        //已登录
                        navController.navigate(Destinations.HomeFrame.route)
                    } else {
                        //未登录
                        LoginScreen {
                            //已登录
                            navController.navigate(Destinations.HomeFrame.route)
                        }
                    }

                }


                // 登錄後進入主頁，可以選擇 Space， 配置 Settings
                composable(
                    Destinations.HomeFrame.route,
                    enterTransition = {
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
                    },
                    exitTransition = {
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Right)
                    },
                ) {

                     if (userViewModel.logged) {
                         MainFrame( onBack = {
                             navController.popBackStack()
                         },
                             onNavigateToSpaceZone = {
                                 navController.navigate(Destinations.SelectTableFrame.route)
                             })
                     }else{
                         //未登录
                        navController.navigate(Destinations.Login.route)
                     }

                }

                //選桌
                composable(
                    Destinations.SelectTableFrame.route,
                    enterTransition = {
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
                    },
                    exitTransition = {
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Right)
                    },
                ) {
                    SelectTableScreen(
                        onNavigateToOrder = {
                            navController.navigate(Destinations.OrderFrame.route)
                        },
                        onBack = {navController.popBackStack()},
                        onLogout = {
                            navController.navigate(Destinations.Login.route)
                        },
                    )
                }

                //點餐
                composable(
                    Destinations.OrderFrame.route,
                    enterTransition = {
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
                    },
                    exitTransition = {
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Right)
                    },
                ) {
                    OrderScreen(
                        onBack = {navController.popBackStack()},
                        onLogout = {
                            navController.navigate(Destinations.Login.route)
                        },
                        attributeViewModel = AttributeViewModel()
                    )
                }

            }
        }
    }
}

@Preview
@Composable
fun NavHostAppPreview() {
    NavHostApp()
}
