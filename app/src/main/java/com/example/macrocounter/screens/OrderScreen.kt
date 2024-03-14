package com.example.macrocounter.screens

import com.example.macrocounter.components.TableCardItem

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TextDecrease
import androidx.compose.material3.FilledTonalButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.macrocounter.R
import com.example.macrocounter.components.AttributePopup
import com.example.macrocounter.components.FoodCardItem
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.example.macrocounter.extension.OnBottomReached
import com.example.macrocounter.components.SpaceItem
import com.example.macrocounter.compositionLocal.LocalFoodViewModel
import com.example.macrocounter.compositionLocal.LocalSpaceViewModel
import com.example.macrocounter.compositionLocal.LocalTableViewModel
import com.example.macrocounter.compositionLocal.LocalUserViewModel
import com.example.macrocounter.model.entity.CartItemForViewEntity
import com.example.macrocounter.model.entity.FoodEntity
import com.example.macrocounter.model.entity.TableEntity
import com.example.macrocounter.viewModel.AttributeViewModel
import com.example.macrocounter.viewModel.SpaceViewModel
import com.example.macrocounter.viewModel.MainViewModel
import com.example.macrocounter.viewModel.UserViewModel
import com.example.macrocounter.components.QuantitySelector
import com.example.macrocounter.model.entity.CartItemEntity
import kotlinx.coroutines.coroutineScope

import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class, com.google.accompanist.pager.ExperimentalPagerApi::class)
@Composable
fun OrderScreen(
    vm: MainViewModel = viewModel(),
    attributeViewModel: AttributeViewModel,
    onBack: () -> Unit = {},
    onLogout: () -> Unit = {},
    onNavigateToOrder: (id: String) -> Unit = {},
) {

    val userViewModel = LocalUserViewModel.current
    val spaceViewModel = LocalSpaceViewModel.current
    val tableViewModel = LocalTableViewModel.current
    val foodViewModel = LocalFoodViewModel.current




    LaunchedEffect(Unit) {

//        //獲取分類
//        userViewModel.userInfo?.token?.let { spaceViewModel.selectedSpace?.let { it1 -> categoryViewModel.fetchFoodList(token = it, spaceId = it1.id) } }

        //獲取餐品
//        userViewModel.userInfo?.token?.let { spaceViewModel.selectedSpace?.let { it1 -> foodViewModel.fetchFoodList(token = it, spaceId = it1.id) } }

    }

    val coroutineScope = rememberCoroutineScope()

    val lazyListState = rememberLazyListState()
//    lazyListState.OnBottomReached(buffer = 3) {
//        coroutineScope.launch { if (vm.showSpaceList) userViewModel.userInfo?.token?.let { spaceViewModel.loadMore(token = it) } }
//    }

    Column(modifier = Modifier) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ){
                IconButton(onClick = {
                    coroutineScope.launch {
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
                "${spaceViewModel.selectedSpace?.name}:${tableViewModel.selectedTable?.label} " +
                        if(tableViewModel.selectedOrder==null)"点餐" else "加餐",
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
                        onLogout()
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

        Row {

            Column (
                modifier = Modifier
                    .width(300.dp)
                    .fillMaxSize()
                    .padding(10.dp)
//                    .verticalScroll(rememberScrollState())
                    .background(color = Color(0xFFF5F5F5))
//                    .border(2.dp, Color.Red, RoundedCornerShape(10.dp)),
            ){

                LazyColumn(

                ){

//                    attributeViewModel.shoppingCart.forEachIndexed { i, e ->

                        itemsIndexed(attributeViewModel.shoppingCart){ i, e ->
                            CartItem(
                                item = e,
                                decreaseItemCount = {
                                    attributeViewModel.decreaseItem(i)
                                },
                                increaseItemCount = {
                                    Log.d("increaseItemCount",
                                        attributeViewModel.shoppingCart[i].quantity.toString()
                                    )
                                    attributeViewModel.addItem(i)
//                            var item = CartItemEntity(food=e.food, spec = e.spec)
//                            attributeViewModel.addToCart(item)
                                },
                                onItemClick={
                                    null
                                },
                                removeItem = {
                                    null
                                }
                            )
                        }

//
//                    }
                }


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ){
                    FilledTonalButton(
                        shape = RoundedCornerShape(5.dp),
                        enabled = tableViewModel.selectedTable!= null,
                        onClick = {
                            // 判斷是點餐還是加餐
                            coroutineScope.launch{
                                if(tableViewModel.selectedOrder == null) {

                                    userViewModel.userInfo?.let {
                                        spaceViewModel.selectedSpace?.let { it1 ->
                                            tableViewModel.selectedTable?.let { it2 ->
                                                attributeViewModel.createTableOrder(
                                                    token = it.token,
                                                    spaceId = it1.id,
                                                    table = it2.id
                                                )
                                            }
                                        }
                                    }
                                }else{
                                    userViewModel.userInfo?.token?.let { attributeViewModel.addToOrder(token = it, orderId = tableViewModel.selectedOrder!!.id) }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(0.9F)
                    ) {
                        Text(
                            text = "提交",
                            textAlign = TextAlign.Center,
                        )
                    }
                }

            }

        Column {
            if(foodViewModel.categories.size > 0) {
                TabRow(
                    selectedTabIndex = vm.categoryIndex,
                    backgroundColor = Color(0x22149EE7),
                    contentColor = Color(0xFF149EE7),
                ) {
                    foodViewModel.categories.forEachIndexed { index, category ->
                        Tab(
                            selected = vm.categoryIndex == index,
                            onClick = {
                                vm.updateCategoryIndex(index)
                            },
                            selectedContentColor = Color(0xFF149EE7),
                            unselectedContentColor = Color(0xFF666666),
                        ) {
                            Text(
                                text = category,
                                modifier = Modifier
                                    .padding(vertical = 8.dp),
//                            .placeholder(visible = !vm.categoryLoaded, color = Color.LightGray),
                                fontSize = 14.sp
                            )
                        }
                    }
                }


//            LazyColumn(state = lazyListState) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 200.dp)
                ){
                    if(foodViewModel.categories.size > 0) {
                        val list = foodViewModel.listWithCategory[foodViewModel.categories[vm.categoryIndex]]?: emptyList()


                        //列表
                        items(list) { food ->
                            FoodCardItem(
                                food,
                                tableViewModel.listLoaded,
                                modifier = Modifier.clickable {
                                    attributeViewModel.onItemClick(food)
//                        onNavigateToOrder(table.id)
//                        tableViewModel.selectedTable = TableEntity(id=table.id, label=table.label)
                                })
                        }
                    }

                }
            }
            //分类标签


        }

        }




    }

    if(attributeViewModel.isDialogShown){
        attributeViewModel.selectedfood?.let {
            AttributePopup(
                onDismiss = {
                    attributeViewModel.onDismissDialog()
                },
                food = it,
                onConfirm = {
                    Log.d("onconfirm", it.toString())
                    attributeViewModel.addToCart(it)
                    //viewmodel.buyItem()
                }
            )
        }
    }
}


@Composable
fun CartItem(
    item: CartItemForViewEntity,
    removeItem: (String) -> Unit,
    increaseItemCount: () -> Unit,
    decreaseItemCount: () -> Unit,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .background(color = Color.White)){
        Column (
            modifier = Modifier
                .fillMaxHeight()
                .padding(20.dp),
        ){
            Image(
                painter = if(item.food.image != null && item.food.image != "") rememberAsyncImagePainter("${item.food.image}?imageView2/1/w/268/q/85") else rememberAsyncImagePainter("https://ordering-1318552943.cos.ap-hongkong.myqcloud.com/static/default.png?imageView2/1/w/268/q/85"),
                contentDescription = "android image",
                modifier = Modifier.size(100.dp)
            )

        }



        Column (
            modifier = Modifier
                .fillMaxHeight()
                .padding(0.dp, 10.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ){
            Text(
                "${item.food.name}",
                textAlign = TextAlign.Center,
            )
            Text(
                "${item.spec}",
                textAlign = TextAlign.Center,
            )

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
            ) {
//                IconButton(onClick = {  },
//                    modifier = Modifier
//                        .then(Modifier.size(24.dp))
//                        .border(1.dp, Color.Cyan, shape = CircleShape)
//                ) {
//                    Icon(Icons.Default.TextDecrease, contentDescription = "content description", tint = Color.Cyan,
//                        modifier = Modifier
//                        .fillMaxSize())
//                }

                RoundedButton(onClick = decreaseItemCount, modifier = Modifier, imageVector = Icons.Default.ArrowBack)
                Text(
                    "${item.quantity}",
                    textAlign = TextAlign.Center,
                )
                RoundedButton(onClick = increaseItemCount, modifier = Modifier, imageVector = Icons.Default.Add)
            }

        }


    }

}


@Composable
fun RoundedButton(modifier: Modifier, onClick: () -> Unit, imageVector: ImageVector) {
    Box(modifier = modifier.padding(horizontal = 10.dp)) {
        Button(
            onClick = onClick,
            shape = CircleShape,
            modifier = modifier.size(20.dp),
            contentPadding = PaddingValues(1.dp)
        ) {
            // Inner content including an icon and a text label
            Icon(
                imageVector = imageVector,
                contentDescription = "Favorite",
                modifier = Modifier.size(10.dp)
            )
        }

    }
}