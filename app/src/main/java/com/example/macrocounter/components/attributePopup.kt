package com.example.macrocounter.components


import android.util.Log
import android.util.MutableInt
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.FilledTonalButton
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
import com.example.macrocounter.model.entity.Attribute
import com.example.macrocounter.model.entity.CartItemEntity
import com.example.macrocounter.model.entity.FoodEntity
import com.example.macrocounter.model.entity.Option
import com.example.macrocounter.model.entity.Spec


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AttributePopup(
    food: FoodEntity,
    onDismiss:()->Unit,
    onConfirm:(selectedAttribute: CartItemEntity)->Unit
) {

    var selectedAttribute = remember { mutableMapOf<String, Option>() }
    var total = remember{ mutableStateOf (food.price) }

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
                .fillMaxWidth(0.50f)
                .border(2.dp, color = Color(0xFF666666), shape = RoundedCornerShape(15.dp))
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                verticalArrangement = Arrangement.spacedBy(25.dp)
            ){



                Text(
                    text = "${food.name}",
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ){

                    for ((index, value) in food.attributes.withIndex()){
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(15.dp)
                        ) {
                            Text(
                                text = "${value.label}",
                                textAlign = TextAlign.Center
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                FilterChipGroup(
                                    items = value.options,
                                    defaultSelectedItemIndex = -1,
                                    onSelectedChanged = {
                                        var tmp: Int = food.price
                                        Log.d("selected", it.toString())
                                        selectedAttribute[value.label] = it

                                        selectedAttribute.forEach { entry ->
                                            tmp += entry.value.extra/100
                                        }
                                        total.value = tmp
                                    }
                                )
                            }
                        }
                    }


                    Divider()

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){

                        Text("總價", fontWeight = FontWeight.Bold)

                        Text("${total.value}", fontWeight = FontWeight.Bold)
                    }


                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(50.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    FilledTonalButton(
                        onClick = {
                            val specList = mutableListOf<Spec>()

                            selectedAttribute.forEach { entry ->
                                specList.add(Spec(attribute = entry.key, optioned = entry.value.label))
                            }
                            val cartItem = CartItemEntity(food = food, spec = specList)

                            onConfirm(cartItem)

                            onDismiss()
                        },
//                        colors = ButtonDefaults.buttonColors(
//                            backgroundColor = Color(0x22149EE7),
////                            contentColor = Color(0xFF666666),
//                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "加入購物車",
//                            style = MaterialTheme.typography.h6,
//                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )
                    }
                }

            }
        }
    }
}
