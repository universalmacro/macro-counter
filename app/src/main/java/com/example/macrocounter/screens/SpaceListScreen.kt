package com.example.macrocounter.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.example.macrocounter.viewModel.SpaceViewModel
import com.example.macrocounter.viewModel.MainViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class, com.google.accompanist.pager.ExperimentalPagerApi::class)
@Composable
fun StudyScreen(
    vm: MainViewModel = viewModel(),
    spaceViewModel: SpaceViewModel = viewModel(),
    onNavigateToSpaceZone: () -> Unit = {},
    onNavigateToVideo: () -> Unit = {},
    onNavigateToStudyHistory: () -> Unit = {}
) {

    LaunchedEffect(Unit) {
//        //獲取分類數據
//        vm.categoryData()
        //獲取空間列表
        spaceViewModel.fetchArticleList()

    }

    val coroutineScope = rememberCoroutineScope()

    val lazyListState = rememberLazyListState()
    lazyListState.OnBottomReached(buffer = 3) {
        coroutineScope.launch { if (vm.showArticleList) spaceViewModel.loadMore()  }
    }

    Column(modifier = Modifier) {
        //标题栏
        TopAppBar(modifier = Modifier.padding(horizontal = 8.dp)) {

            //搜索按钮
            Surface(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .weight(1f),
                color = Color(0x33FFFFFF)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )

                    Text(
                        "搜索感兴趣的资讯或课程",
                        color = Color.White,
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            //学习进度
            Text(
                text = "学习\n进度",
                fontSize = 10.sp,
                color = Color.White,
                modifier = Modifier.clickable {
                    onNavigateToStudyHistory()
                })

            Spacer(modifier = Modifier.width(8.dp))

            Text("26%", fontSize = 12.sp, color = Color.White)

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = null,
                tint = Color.White
            )

        }

//        //分类标签
//        TabRow(
//            selectedTabIndex = vm.categoryIndex,
//            backgroundColor = Color(0x22149EE7),
//            contentColor = Color(0xFF149EE7),
//        ) {
//            vm.categories.forEachIndexed { index, category ->
//                Tab(
//                    selected = vm.categoryIndex == index,
//                    onClick = {
//                        vm.updateCategoryIndex(index)
//                    },
//                    selectedContentColor = Color(0xFF149EE7),
//                    unselectedContentColor = Color(0xFF666666),
//                ) {
//                    Text(
//                        text = category.label,
//                        modifier = Modifier
//                            .padding(vertical = 8.dp)
//                            .placeholder(visible = !vm.categoryLoaded, color = Color.LightGray),
//                        fontSize = 14.sp
//                    )
//                }
//            }
//        }


        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = if (vm.showArticleList) spaceViewModel.refreshing else false),
            onRefresh = { coroutineScope.launch { if (vm.showArticleList) spaceViewModel.refresh() } }
        ) {
            LazyColumn(state = lazyListState) {

                if (vm.showArticleList) {
                    //文章列表
                    items(spaceViewModel.list) { article ->
                        SpaceItem(
                            article,
                            spaceViewModel.listLoaded,
                            modifier = Modifier.clickable {
                                onNavigateToSpaceZone()
                            })
                    }
                }
            }
        }

    }
}


@Preview
@Composable
fun StudyScreenPreview() {
    StudyScreen()
}
