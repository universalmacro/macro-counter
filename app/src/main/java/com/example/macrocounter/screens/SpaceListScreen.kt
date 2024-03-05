package com.example.macrocounter.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.example.macrocounter.compositionLocal.LocalSpaceViewModel
import com.example.macrocounter.compositionLocal.LocalTableViewModel
import com.example.macrocounter.compositionLocal.LocalUserViewModel
import com.example.macrocounter.model.entity.SpaceEntity
import com.example.macrocounter.viewModel.SpaceViewModel
import com.example.macrocounter.viewModel.MainViewModel
import com.example.macrocounter.viewModel.UserViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class, com.google.accompanist.pager.ExperimentalPagerApi::class)
@Composable
fun SpaceListScreen(
    vm: MainViewModel = viewModel(),
    onBack: () -> Unit = {},
    onNavigateToSpaceZone: (id: String) -> Unit = {},
    onNavigateToStudyHistory: () -> Unit = {}
) {

    val userViewModel = LocalUserViewModel.current
    val spaceViewModel = LocalSpaceViewModel.current
    val tableViewModel = LocalTableViewModel.current

    LaunchedEffect(Unit) {
//
        //獲取空間列表
        userViewModel.userInfo?.token?.let { spaceViewModel.fetchSpaceList(token = it) }

    }

    val coroutineScope = rememberCoroutineScope()

    val lazyListState = rememberLazyListState()
//    lazyListState.OnBottomReached(buffer = 3) {
//        coroutineScope.launch { if (vm.showSpaceList) userViewModel.userInfo?.token?.let { spaceViewModel.loadMore(token = it) } }
//    }

    Column(modifier = Modifier) {

        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ){
                IconButton(onClick = {
                    coroutineScope.launch {
//                        tableViewModel.selectedTable = null
                        onBack()
                    }
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "返回",
                    )
                }
            }


            Text(
                "選擇 SPACE",
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
            ) {
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

        }

        SwipeRefresh(
            modifier = Modifier.padding(20.dp, 0.dp),
            state = rememberSwipeRefreshState(isRefreshing = spaceViewModel.refreshing ),
            onRefresh = { coroutineScope.launch { userViewModel.userInfo?.token?.let {
                spaceViewModel.refresh(
                    it
                )
            } } }
        ) {
//            LazyColumn(state = lazyListState) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 250.dp)
            ){
                //文章列表
                items(spaceViewModel.list) { space ->
                    SpaceItem(
                        space,
                        spaceViewModel.listLoaded,
                        modifier = Modifier.clickable {
                            onNavigateToSpaceZone(space.id)
                            spaceViewModel.selectedSpace = SpaceEntity(id=space.id, name=space.name, description=space.description)
                        })
                }
            }


        }


    }
}






@Preview
@Composable
fun SpaceListScreenPreview() {
    SpaceListScreen()
}
