package com.schoolproject.droidshop.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.schoolproject.droidshop.ui.theme.poppinsFontFamily

@Composable
fun SectionText(
    text1: String,
    text2: String,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text1,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .weight(1f),
            fontSize = 18.sp,
            fontFamily = poppinsFontFamily
        )
        Text(
            text = text2,
            fontWeight = FontWeight.Medium,
            modifier = Modifier,
            fontFamily = poppinsFontFamily
        )
    }

}