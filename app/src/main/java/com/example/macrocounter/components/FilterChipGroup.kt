package com.example.macrocounter.components


import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.macrocounter.model.entity.Option

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterChipGroup(
    items: List<Option>,
    defaultSelectedItemIndex:Int = 0,
    selectedItemIcon: ImageVector = Icons.Filled.Done,
    itemIcon: ImageVector = Icons.Filled.Build,
    onSelectedChanged : (Option) -> Unit = {}
){
    var selectedItemIndex by remember { mutableStateOf(defaultSelectedItemIndex) }

    LazyRow(userScrollEnabled = true) {

        items(items.size) { index: Int ->
            FilterChip(
                modifier = Modifier.padding(end = 6.dp),
                selected = if(selectedItemIndex>=0) items[selectedItemIndex] == items[index] else false,
                onClick = {
                    selectedItemIndex = index
                    onSelectedChanged(items[index])
                },
                label = { Text("${items[index].label}  +${items[index].extra/100}") },
//                leadingIcon = if (items[selectedItemIndex] == items[index]) {
//                    {
//                        Icon(
//                            imageVector = selectedItemIcon,
//                            contentDescription = "Localized Description",
//                            modifier = Modifier.size(FilterChipDefaults.IconSize)
//                        )
//                    }
//                } else {
//                    {
//                        Icon(
//                            imageVector = itemIcon,
//                            contentDescription = "Localized description",
//                            modifier = Modifier.size(FilterChipDefaults.IconSize)
//                        )
//                    }
//                }
            )
        }
    }
}