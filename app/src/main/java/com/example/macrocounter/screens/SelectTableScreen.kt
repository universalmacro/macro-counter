package com.example.macrocounter.screens

import com.example.macrocounter.components.TableCardItem
import com.example.macrocounter.viewModel.TableViewModel


import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.example.macrocounter.extension.OnBottomReached
import com.example.macrocounter.components.SpaceItem
import com.example.macrocounter.compositionLocal.LocalTableViewModel
import com.example.macrocounter.compositionLocal.LocalUserViewModel
import com.example.macrocounter.viewModel.SpaceViewModel
import com.example.macrocounter.viewModel.MainViewModel
import com.example.macrocounter.viewModel.UserViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class, com.google.accompanist.pager.ExperimentalPagerApi::class)
@Composable
fun SelectTableScreen(
    vm: MainViewModel = viewModel(),
    onBack: () -> Unit = {},
    onNavigateToOrder: (id: String) -> Unit = {},
) {

    val userViewModel = LocalUserViewModel.current
    val tableViewModel = LocalTableViewModel.current



    LaunchedEffect(Unit) {
//
        //獲取餐桌列表
        userViewModel.userInfo?.token?.let { tableViewModel.fetchTableList(token = it, spaceId = tableViewModel.selectedTable) }

    }

    val coroutineScope = rememberCoroutineScope()

    val lazyListState = rememberLazyListState()
//    lazyListState.OnBottomReached(buffer = 3) {
//        coroutineScope.launch { if (vm.showSpaceList) userViewModel.userInfo?.token?.let { spaceViewModel.loadMore(token = it) } }
//    }

    Column(modifier = Modifier) {

        Row (
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
        ){
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


            LazyColumn(state = lazyListState) {

                //列表
                items(tableViewModel.list) { table ->
                    TableCardItem(
                        table,
                        tableViewModel.listLoaded,
                        modifier = Modifier.clickable {
                            onNavigateToOrder(table.id)
                            tableViewModel.selectedTable = table.id
                        })
                }

            }




    }
}
