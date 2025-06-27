package com.schoolproject.droidshop.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.schoolproject.droidshop.domain.model.Cart
import com.schoolproject.droidshop.ui.theme.poppinsFontFamily
import kotlinx.coroutines.flow.Flow

@Composable
fun CartItem(
    modifier: Modifier = Modifier,
    cart: Cart,
    orderCount: Int,
    onQuantityIncrease: (Int) -> Unit,
    onQuantityDecrease: (Int) -> Unit,
    onCheckedChange: (Int) -> Unit,
    onRemove: (Int) -> Unit
){

    var checked by remember { mutableStateOf(false) }
    var quantity by remember { mutableStateOf(orderCount) }

    val totalPrice = cart.productPrice * quantity

    LaunchedEffect(cart.isChecked){
        checked = cart.isChecked
    }



    Row(
        modifier = modifier
            .padding(
                vertical = 8.dp,
                horizontal = 8.dp
            )
            .height(120.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {

       Checkbox(
           checked = checked ,
           onCheckedChange = {
               checked = it
               onCheckedChange(cart.id)

           }
       )

       AsyncImage(
           modifier = Modifier
               .size(90.dp)
               .align(Alignment.CenterVertically),
           model = cart.productImage,
           contentDescription = null,
           contentScale = ContentScale.Crop
       )
       Column(
           modifier = Modifier
               .fillMaxWidth()
               .padding(start = 16.dp)
               .weight(1f)
       ){
           Text(
               text = cart.productName,
               fontFamily = poppinsFontFamily,
               fontSize = 14.sp,
               fontWeight = FontWeight.Medium,
               maxLines = 2
           )
           Text(
               text = "$${"%.2f".format(totalPrice)}",
               fontFamily = poppinsFontFamily,
               fontWeight = FontWeight.Medium
           )
       }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
                .weight(1f)
        ) {

            ProductCounter(
                orderCount = orderCount,
                onIncrement = {
                    onQuantityIncrease(cart.id)
                },
                onDecrement = {
                    onQuantityDecrease(cart.id)
                }
            )

            Button(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(4.dp)
                ,
                onClick = {
                    onRemove(cart.id)
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.DeleteOutline,
                    contentDescription = null
                )
            }
        }
    }
}
