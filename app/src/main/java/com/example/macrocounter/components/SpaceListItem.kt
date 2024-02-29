package com.example.macrocounter.components

import androidx.compose.foundation.layout.*
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


/**
 * 文章列表 item
 *
 * @param space
 * @param modifier
 */
@Composable
fun SpaceItem(space: SpaceEntity, loaded: Boolean, modifier: Modifier = Modifier) {

    Column(modifier = modifier.padding(8.dp)) {
        Text(
            text = space.name,
            color = Color(0xFF333333),
            fontSize = 16.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .placeholder(visible = !loaded, highlight = PlaceholderHighlight.shimmer())
        )

        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text(
                "${space.description}",
                color = Color(0xFF999999),
                fontSize = 10.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .placeholder(visible = !loaded, highlight = PlaceholderHighlight.shimmer())
            )

//            Text(
//                space.timestamp,
//                color = Color(0xFF999999),
//                fontSize = 10.sp,
//                maxLines = 1,
//                overflow = TextOverflow.Ellipsis,
//                modifier = Modifier
//                    .placeholder(visible = !loaded, highlight = PlaceholderHighlight.shimmer())
//            )
        }

        Spacer(Modifier.height(8.dp))

        Divider()
    }

}
