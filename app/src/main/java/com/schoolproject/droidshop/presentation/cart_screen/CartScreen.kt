package com.schoolproject.droidshop.presentation.cart_screen

import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.schoolproject.droidshop.core.util.Resource
import com.schoolproject.droidshop.presentation.components.CartItem
import com.schoolproject.droidshop.ui.theme.poppinsFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    viewModel: CartViewModel = hiltViewModel(),
    navigateToMainScreen: () -> Unit,
    navigateToCheckOutScreen: () -> Unit
){

    val cartItemsResource by viewModel.cartItems.collectAsStateWithLifecycle()
    val totalPrice by viewModel.totalPrice.collectAsStateWithLifecycle()
    val isCheckoutEnabled by viewModel.isCheckoutEnabled.collectAsStateWithLifecycle()
    val isAllChecked by viewModel.isAllChecked.collectAsStateWithLifecycle()


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .padding(horizontal = 8.dp),
            title = {
                    Text(
                        text = "My Cart",
                        fontFamily = poppinsFontFamily
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                navigateToMainScreen()
                            }
                    )
                }
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {

                        cartItemsResource.let { carts ->
                            if(carts.isNotEmpty()) {
                                LazyColumn {
                                    items(carts) { cart ->
                                        CartItem(
                                            cart= cart,
                                            orderCount = cart.productQuantity,
                                            onQuantityDecrease = {
                                                viewModel.decreaseQuantity(it)
                                            },
                                            onQuantityIncrease = {
                                                viewModel.increaseQuantity(it)
                                            },
                                            onCheckedChange = {
                                                viewModel.toggleChecked(it)
                                            },
                                            onRemove = {
                                                viewModel.removeCart(it)
                                            }
                                        )
                                    }
                                }
                            }
                        }


                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .height(85.dp)
                        .align(Alignment.BottomCenter)

                ) {
                    HorizontalDivider()
                    Row(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth()
                    ){
                        Checkbox(
                            checked = isAllChecked,
                            onCheckedChange = {
                                viewModel.toggleCheckedAllCartItem(it)

                            }
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                fontSize = 14.sp,
                                text = "Total",
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = poppinsFontFamily
                            )
                            Text(
                                modifier = Modifier.weight(1f),
                                text = "$${"%.2f".format(totalPrice)}",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = poppinsFontFamily
                            )
                        }


                        Button(
                            onClick = {
                                navigateToCheckOutScreen()
                            },
                            enabled = isCheckoutEnabled,
                            modifier = Modifier
                                .height(55.dp)
                                .width(120.dp),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                text = "Checkout",
                                fontSize = 14.sp,
                                fontFamily = poppinsFontFamily,
                                color = Color.White
                            )

                        }
                    }

                }
            }
        }
    )
}


@Composable
fun CartsContent() {

}