package com.example.macrocounter.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.material3.Card
import com.example.macrocounter.model.entity.TableEntity


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.example.macrocounter.model.entity.SpaceEntity


@Composable
fun TableCardItem(table: TableEntity, modifier: Modifier = Modifier, selected: Boolean, hasOrder: Boolean) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(15.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        shape = RoundedCornerShape(8.dp),
        border = if (selected) BorderStroke(2.dp, Color.Black) else BorderStroke(0.dp, Color.Transparent),
        colors = CardDefaults.cardColors(
            containerColor = if(hasOrder) Color(0xFFFFE4E1) else Color.White),
        ) {
        Row (
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Text(text = "${table.label}",
                fontSize = 22.sp,
                modifier = Modifier.padding(10.dp)
            )
        }

    }

}
