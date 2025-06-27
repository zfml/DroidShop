package com.schoolproject.droidshop.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.schoolproject.droidshop.ui.theme.poppinsFontFamily

@Composable
fun ProductCounter(
    modifier: Modifier = Modifier,
    orderCount: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
){
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .size(width = 110.dp, height = 40.dp)
            .padding(4.dp)
            .border(
                border = BorderStroke(
                    1.dp,
                    Color("#cdcdcd".toColorInt())
                ),
                shape = RoundedCornerShape(10.dp)
            )
    ){
        Surface(
            shape = RoundedCornerShape(size = 5.dp),
            color = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.outline,
            modifier = Modifier.size(30.dp)
        ) {
            Text(
                text = "—",
                fontSize = 22.sp,
                color = Color("#cdcdcd".toColorInt()),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        onDecrement()
                    }
            )
        }

        Text(
            fontFamily = poppinsFontFamily,
            text = orderCount.toString(),
            modifier = Modifier
                .weight(1f)
                .testTag("count"),
            fontSize = 23.sp,
            textAlign = TextAlign.Center,
        )
        Surface(
            shape = RoundedCornerShape(size = 5.dp),
            color = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(30.dp)
        ) {
            Text(
                text = "＋",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .clickable { onIncrement() }
            )
        }
    }
}