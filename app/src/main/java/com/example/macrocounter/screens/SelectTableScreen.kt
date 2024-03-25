package com.example.macrocounter.screens

import com.example.macrocounter.components.TableCardItem


import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.macrocounter.components.OrderPopup
import com.example.macrocounter.compositionLocal.LocalFoodViewModel
import com.example.macrocounter.compositionLocal.LocalSpaceViewModel
import com.example.macrocounter.compositionLocal.LocalTableViewModel
import com.example.macrocounter.compositionLocal.LocalUserViewModel
import com.example.macrocounter.model.entity.TableEntity
import com.example.macrocounter.model.service.OrderService
import com.example.macrocounter.viewModel.MainViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.time.Duration.Companion.seconds
import androidx.compose.material3.ButtonColors
import com.example.macrocounter.components.CustomDialog


private fun getDateTime(s: String): String? {
    try {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val netDate = Date(s.toLong() * 1000)
        return sdf.format(netDate)
    } catch (e: Exception) {
        return e.toString()
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalPagerApi::class,
    ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class
)
@Composable
fun SelectTableScreen(
    vm: MainViewModel = viewModel(),
    onBack: () -> Unit = {},
    onLogout: () -> Unit = {},
    onNavigateToOrder: (id: String) -> Unit = {},
) {

    val userViewModel = LocalUserViewModel.current
    val spaceViewModel = LocalSpaceViewModel.current
    val tableViewModel = LocalTableViewModel.current
    val foodViewModel = LocalFoodViewModel.current


    val showingDialog = remember { mutableStateOf(false) }
    var selectedOrderIds = remember { mutableStateListOf<String>() }
    val amount = remember { mutableStateOf<String>("") }

    val message = remember { mutableStateOf("Edit Me") }
    val title = remember { mutableStateOf("請輸入金額") }


    val openDialog = remember { mutableStateOf(false) }
    val openPrint = remember { mutableStateOf("ADD") }

    val editMessage = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {

        //獲取餐品
        userViewModel.userInfo?.token?.let { spaceViewModel.selectedSpace?.let { it1 -> foodViewModel.fetchFoodList(token = it, spaceId = it1.id) } }

//
        //獲取餐桌列表
        userViewModel.userInfo?.token?.let { spaceViewModel.selectedSpace?.let { it1 -> tableViewModel.fetchTableList(token = it, spaceId = it1.id) } }

        // 獲取 order
//        userViewModel.userInfo?.token?.let { spaceViewModel.selectedSpace?.let { it1 -> tableViewModel.fetchOrderList(token = it, spaceId = it1.id) } }

    }

    LaunchedEffect(Unit) {

//        userViewModel.userInfo?.token?.let { spaceViewModel.selectedSpace?.let { it1 -> tableViewModel.fetchOrderList(token = it, spaceId = it1.id, statuses= listOf("SUBMITTED")) } }

        // 間隔 3秒 獲取 order
        while(true) {
            userViewModel.userInfo?.token?.let { spaceViewModel.selectedSpace?.let { it1 -> tableViewModel.fetchOrderList(token = it, spaceId = it1.id, statuses= listOf("SUBMITTED")) } }
            delay(3.seconds)
        }


    }

    val coroutineScope = rememberCoroutineScope()

    val lazyListState = rememberLazyListState()
//    lazyListState.OnBottomReached(buffer = 3) {
//        coroutineScope.launch { if (vm.showSpaceList) userViewModel.userInfo?.token?.let { spaceViewModel.loadMore(token = it) } }
//    }


    if (showingDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showingDialog.value = false
            },

            title = { Text(text = "操作失敗：${tableViewModel.error}") },

            confirmButton = {
                TextButton(
                    onClick = {
                        showingDialog.value = false
                    },
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text("確認")
                }
            },
        )
    }

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
                        spaceViewModel.selectedSpace = null
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
                "SPACE:${spaceViewModel.selectedSpace?.name} 選擇餐桌",
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
                    .width(400.dp)
                    .fillMaxSize()
                    .padding(10.dp)
                    .verticalScroll(rememberScrollState())
//                    .border(2.dp, Color.Red, RoundedCornerShape(10.dp)),
            ){

                var selectedTableOrder = tableViewModel.orderList

                if(tableViewModel.selectedTable!=null)
                    selectedTableOrder = tableViewModel.orderList.filter { it.tableLabel == tableViewModel.selectedTable?.id}
                        .toTypedArray()


                if(selectedTableOrder.isNotEmpty()){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp),
                    ) {
                        Row(modifier = Modifier.weight(1f)) {
                            Checkbox(checked = selectedOrderIds.size == selectedTableOrder.size,
                                onCheckedChange = {
                                    if(selectedOrderIds.size == selectedTableOrder.size){
                                        selectedOrderIds.clear()
                                    }else{
                                        selectedOrderIds.clear()
                                        selectedTableOrder.forEach { it -> selectedOrderIds.add(it.id)}
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = if(selectedOrderIds.size == selectedTableOrder.size) "取消全選" else "全選",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Color.Black,
                                modifier = Modifier.align(CenterVertically)
                            )
                        }
                    }

                }


                // billList
                selectedTableOrder.forEachIndexed { i, e ->


                    // Checkbox with text on right side
                    Row(modifier = Modifier
                        .fillMaxWidth()
//                        .height(40.dp)
                        .clickable(
                            role = Role.Checkbox,
                            onClick = {
                                if (selectedOrderIds.contains(e.id)) {
                                    selectedOrderIds.remove(e.id)
                                } else {
                                    selectedOrderIds.add(e.id)
                                }
                            }
                        )
                        .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = selectedOrderIds.contains(e.id),
                            onCheckedChange = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))


                        Card(
                            elevation = 10.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp)
                                .clickable {
//                                coroutineScope.launch {
//                                    userViewModel.userInfo?.token?.let { spaceViewModel.selectedSpace?.let { it1 -> tableViewModel.fetchOrderList(token = it, spaceId = it1.id) } }
//                                }
//                               // 彈出 bill詳情
                                    tableViewModel.selectedOrder = e
                                    tableViewModel.selectedOrderShow = e
                                    tableViewModel.selectedOrderFoods = mutableStateListOf()
                                    e.foods.forEach { it -> tableViewModel.selectedOrderFoods.add(it) }
                                    tableViewModel.isDialogShown = true
                                },
                            shape = RoundedCornerShape(2.dp),
                            backgroundColor = if (e.status == "SUBMITTED")  Color(0xFFE6E6FA)  else if(e.status == "Cancel") Color.LightGray else Color.Cyan

                        ) {
                            Column (modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)){
                                Text(text = "桌號：${e.tableLabel}")
                                Text(text = "狀態：${e.status}")
                                Text(text = "時間：${getDateTime(e.createdAt)}")

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(0.dp, 10.dp, 0.dp, 0.dp)
                                ){
                                    FilledTonalButton(
                                        shape = RoundedCornerShape(5.dp),
                                        onClick = {
                                            coroutineScope.launch{
                                                tableViewModel.selectedOrder = e

                                                openDialog.value = true
                                                openPrint.value = "TABLE"

                                                title.value = "請輸入桌號"


                                            }
                                        },
                                    ) {
                                        Text(
                                            text = "更換桌號",
                                            textAlign = TextAlign.Center,
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))


                                    FilledTonalButton(
                                        shape = RoundedCornerShape(5.dp),
                                        onClick = {
                                            tableViewModel.selectedOrder = e

                                            tableViewModel.selectedTable = TableEntity(id=e.tableLabel, label=e.tableLabel)
                                            tableViewModel.selectedOrder?.tableLabel?.let { onNavigateToOrder(it) }
                                        },
                                    ) {
                                        Text(
                                            text = "加單",
                                            textAlign = TextAlign.Center,
                                        )
                                    }
                                }

                            }
                        }
                    }



                }


                Column (
                    modifier = Modifier.fillMaxWidth(),
                ){
                    
                    if(selectedTableOrder.isNotEmpty()){
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
//                        horizontalArrangement = Arrangement.SpaceBetween,
                        ){
                            FilledTonalButton(
                                shape = RoundedCornerShape(5.dp),
                                enabled = selectedOrderIds.size > 0,
                                onClick = {
                                    coroutineScope.launch{
                                        openDialog.value = true
                                        openPrint.value = "PRINT"
                                    }
                                },
//                            modifier = Modifier.fillMaxWidth(0.5F)
                            ) {
                                Text(
                                    text = "打印訂單",
                                    textAlign = TextAlign.Center,
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))


                            FilledTonalButton(
                                shape = RoundedCornerShape(5.dp),
                                enabled = selectedOrderIds.size > 0,
                                onClick = {
                                    openDialog.value = true
                                    openPrint.value = "FIN"
                                    title.value = "請輸入金額"
                                },
                            ) {
                                Text(
                                    text = "完成訂單",
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                    }


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ){
                        FilledTonalButton(
                            shape = RoundedCornerShape(5.dp),
                            enabled = tableViewModel.selectedTable!= null,
                            onClick = {
                                coroutineScope.launch{
                                    tableViewModel.selectedTable?.let { onNavigateToOrder(it.id) }
                                    // 清除選擇的 order
                                    tableViewModel.selectedOrder = null
                                    tableViewModel.selectedOrderShow = null
                                }
                            },
                            modifier = Modifier.fillMaxWidth(0.9F)
                        ) {
                            Text(
                                text = "點餐",
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            }


            Divider(
                color = Color.LightGray,
                modifier = Modifier
                    .fillMaxHeight()  //fill the max height
                    .width(1.dp)
            )

            Column {

                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 150.dp)
                    ){
                        var hasOrderTable = tableViewModel.orderList.map { it -> it.tableLabel }
                        //列表
                        items(tableViewModel.list) { table ->
                            TableCardItem(
                                table,
                                modifier = Modifier.clickable {
                                    selectedOrderIds.clear()
                                    tableViewModel.selectedTable = TableEntity(id=table.id, label=table.label)
                                },
                                selected = tableViewModel.selectedTable?.id  == table.id,
                                hasOrder = hasOrderTable.contains(table.id)
                            )
                        }

                    }



            }

        }


    }

    if(tableViewModel.isDialogShown){
        tableViewModel.selectedOrder?.let {
            OrderPopup(
                onDismiss = {
                    tableViewModel.onDismissDialog()
                },
                onConfirm = {
                    Log.d("onconfirm", it.toString())
                    coroutineScope.launch {
                        userViewModel.userInfo?.token?.let { it1 -> tableViewModel?.selectedOrder?.id?.let { it2 -> tableViewModel.cancelFromOrder(token= it1, orderId = it2) } }

                        if(tableViewModel.error != ""){
                            showingDialog.value = true
                        }
                    }



                    tableViewModel.onDismissDialog()
                },
                onNavigateToOrder = onNavigateToOrder
//                onNavigateToOrder = {
//                    tableViewModel.onDismissDialog()
//                    tableViewModel.selectedTable?.let { onNavigateToOrder(it.id) }
//                }
            )
        }
    }

    if (openDialog.value) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = contentColorFor(MaterialTheme.colors.background)
                        .copy(alpha = 0.6f)
                )
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {
                        openDialog.value = false
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            CustomDialog(title, message, openDialog, amount, onOk = {
                coroutineScope.launch{
                    Log.d("=========================", openPrint.value)
                    if(openPrint.value == "PRINT"){
                        userViewModel.userInfo?.token?.let {
                            tableViewModel.printBill(token = it, printBillRequest = OrderService.CreateBillRequest(
                                orderIds = selectedOrderIds, amount = amount.value.toInt()))
                        }
                    }else if(openPrint.value == "FIN"){
                        userViewModel.userInfo?.token?.let {
                            tableViewModel.createBill(token = it, createBillRequest = OrderService.CreateBillRequest(
                                orderIds = selectedOrderIds, amount = amount.value.toInt()))
                        }
                    }else if(openPrint.value == "TABLE"){
                        // 更換桌號 switchTable
                        userViewModel.userInfo?.token?.let {
                            tableViewModel.selectedOrder?.let { it1 ->
                                tableViewModel.switchTable(token = it, updateLabelRequest = OrderService.UpdateLabelRequest(
                                    tableLabel = amount.value), orderId = it1.id)
                            }
                        }
                    }
                    // 清除選擇的 order
                    selectedOrderIds.clear()
                }
            })
        }
    }

}
