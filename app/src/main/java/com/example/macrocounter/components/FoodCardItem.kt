package com.example.macrocounter.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.macrocounter.model.entity.FoodEntity


@Composable
fun FoodCardItem(food: FoodEntity, loaded: Boolean, modifier: Modifier = Modifier) {
    val paddingModifier  = Modifier.padding(10.dp)
    Card(
        elevation = 10.dp,
        modifier = modifier
            .fillMaxWidth()
            .padding(15.dp),
        shape = RoundedCornerShape(8.dp),
    ) {

        Column(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = if(food.image != null && food.image != "") rememberAsyncImagePainter("${food.image}?imageView2/1/w/268/q/85") else rememberAsyncImagePainter("https://ordering-1318552943.cos.ap-hongkong.myqcloud.com/static/default.png?imageView2/1/w/268/q/85"),
                contentDescription = "android image",
                modifier = Modifier.size(100.dp)
            )

            Row(

                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ){
                Text(text = "${food.name}",
                    fontSize = 16.sp,
                    modifier = paddingModifier
                )
                Text(text = "$${food.price}",
                    fontSize = 16.sp,
                    modifier = paddingModifier
                )
            }

        }

    }

}
