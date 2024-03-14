package com.example.macrocounter.components

import android.util.Log
import android.util.MutableInt
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.TextDecrease
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.rememberAsyncImagePainter
import com.example.macrocounter.compositionLocal.LocalTableViewModel
import com.example.macrocounter.model.entity.Attribute
import com.example.macrocounter.model.entity.CartItemEntity
import com.example.macrocounter.model.entity.CartItemForViewEntity
import com.example.macrocounter.model.entity.FoodEntity
import com.example.macrocounter.model.entity.Option
import com.example.macrocounter.model.entity.Spec
import com.example.macrocounter.model.entity.TableEntity
import com.example.macrocounter.screens.CartItem


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OrderPopup(
//    food: FoodEntity,
    onDismiss:()->Unit,
    onConfirm:()->Unit,
    onNavigateToOrder: (id: String)->Unit,
) {

    var selectedAttribute = remember { mutableMapOf<String, Option>() }
    val tableViewModel = LocalTableViewModel.current

    Dialog(
        onDismissRequest = {
            onDismiss()
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),

        ) {
        Card(
            elevation = 5.dp,
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth(0.60f)
                .fillMaxHeight(0.8f)
                .border(2.dp, color = Color(0xFF666666), shape = RoundedCornerShape(15.dp))
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
//                verticalArrangement = Arrangement.spacedBy(25.dp)
                verticalArrangement = Arrangement.SpaceBetween
            ){
                Column (
                    modifier = Modifier
                        .fillMaxHeight(0.90f)
                        .verticalScroll(rememberScrollState())
                ) {
                Text(text = "桌號${tableViewModel.selectedOrder?.tableLabel}")

                    tableViewModel.selectedOrderFoods?.forEachIndexed { i, e ->
                        CartItemForOrder(
                            item = e,
                            onItemClick={
                                null
                            },
                            removeItem = {
                                tableViewModel.deleteFood(i)
                            }
                        )
//
                    }

                }


                Row(

                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Button(
                        onClick = onConfirm,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF149EE7),
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                        ,
                        shape = CircleShape
                    ) {
                        Text(
                            text = "提交",
                            style = MaterialTheme.typography.h6,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )
                    }
                    Button(
                        onClick = {
                            onDismiss()
                            Log.d("onClick add order", "${tableViewModel.selectedTable?.id}")
                            tableViewModel.selectedTable = tableViewModel.selectedOrder?.tableLabel?.let { TableEntity(id = it, label = it) }
                            tableViewModel.selectedOrder?.tableLabel?.let { onNavigateToOrder(it) }
                        },

                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White,
                            contentColor = Color.DarkGray
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        shape = CircleShape
                    ) {
                        Text(
                            text = "加單",
                            style = MaterialTheme.typography.h6,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )
                    }
                }

            }
        }
    }


}


@Composable
fun CartItemForOrder(
    item: CartItemEntity?,
    removeItem: () -> Unit,

    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    Row (modifier = Modifier.height(100.dp)){
        Column (
            modifier = Modifier
                .fillMaxHeight()
                .padding(20.dp),
        ){
            Image(
                painter = if(item?.food?.image != null && item.food.image != "") rememberAsyncImagePainter("${item.food.image}?imageView2/1/w/268/q/85") else rememberAsyncImagePainter("https://ordering-1318552943.cos.ap-hongkong.myqcloud.com/static/default.png?imageView2/1/w/268/q/85"),
                contentDescription = "android image",
                modifier = Modifier.size(100.dp)
            )

        }



        Column (
            modifier = Modifier
                .fillMaxHeight()
                .padding(0.dp, 20.dp),

        ){
            Row (
                modifier = Modifier.fillMaxWidth(0.8F),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Column (
                    modifier = Modifier
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        "${item?.food?.name}",
                        textAlign = TextAlign.Center,
                    )
                    Text(
                        "${item?.spec}",
                        textAlign = TextAlign.Center,
                    )
                }



                    TextButton(onClick = removeItem) {
                        Text("刪除", color = Color.Red)
                    }




            }



        }



    }

    Divider()
}
