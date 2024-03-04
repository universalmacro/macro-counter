package com.example.macrocounter.components


import android.util.Log
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.macrocounter.compositionLocal.LocalTableViewModel
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.example.macrocounter.compositionLocal.LocalUserViewModel
import com.example.macrocounter.navigation.Destinations
import com.example.macrocounter.screens.LoginScreen
import com.example.macrocounter.screens.MainFrame
import com.example.macrocounter.screens.SelectTableScreen
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

        CompositionLocalProvider(LocalUserViewModel provides UserViewModel(LocalContext.current), LocalTableViewModel provides TableViewModel(LocalContext.current)) {

            val userViewModel = LocalUserViewModel.current

            AnimatedNavHost(
                navController = navController,
//                startDestination = Destinations.HomeFrame.route
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
                    LoginScreen {
                        //已登录
//                        navController.popBackStack()
                        navController.navigate(Destinations.HomeFrame.route)
                    }
//                    if (userViewModel.logged) {
//                        //已登录
//                        MainFrame(onNavigateToArticle = {
//
////                        navController.navigate(Destinations.ArticleDetail.route)
//                        }, onNavigateToVideo = {
////                        navController.navigate(Destinations.VideoDetail.route)
//                        }, onNavigateToSpace = {
//                            Log.i("===", "onNavigateToSelectTable")
//                            if (userViewModel.logged) {
//                                //已登录
//                                navController.navigate(Destinations.HomeFrame.route)
//                            } else {
//                                //未登录
//                                navController.navigate(Destinations.Login.route)
//                            }
//                        })
//                    } else {
//                        //未登录
//                        navController.navigate(Destinations.Login.route)
//                    }

                }


                // SpaceList
                composable(
                    Destinations.HomeFrame.route,
                    enterTransition = {
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
                    },
                    exitTransition = {
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Right)
                    },
                ) {
                    MainFrame( onBack = {
                        navController.popBackStack()
                    },
                        onNavigateToSpaceZone = {
                            navController.navigate(Destinations.SelectTableFrame.route)
                        })
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
                        onBack = {
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
//                    SelectTableScreen(
//                        onNavigateToOrder = {
//                            navController.navigate(Destinations.OrderFrame.route)
//                        },
//                        onBack = {
//                            navController.navigate(Destinations.Login.route)
//                        },
//                    )
                }

//                composable(
//                    Destinations.Login.route,
//                    enterTransition = {
//                        slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
//                    },
//                    exitTransition = {
//                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Right)
//                    },
//                ) {
//                    LoginScreen()
////                    LoginScreen {
////                        navController.popBackStack()
////                    }
//                }

            }
        }
    }
}

@Preview
@Composable
fun NavHostAppPreview() {
    NavHostApp()
}
