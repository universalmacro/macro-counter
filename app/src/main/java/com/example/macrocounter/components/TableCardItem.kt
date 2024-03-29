package com.example.macrocounter.components

import androidx.compose.material.Card
import com.example.macrocounter.model.entity.TableEntity


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
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
fun TableCardItem(table: TableEntity, loaded: Boolean, modifier: Modifier = Modifier) {
    val paddingModifier  = Modifier.padding(10.dp)
    Card(
            elevation = 10.dp,
            modifier = modifier
            .fillMaxWidth()
            .padding(15.dp),
            shape = RoundedCornerShape(8.dp),
        ) {
        Text(text = "${table.label}",
            fontSize = 22.sp,
            modifier = paddingModifier
        )
    }

}
