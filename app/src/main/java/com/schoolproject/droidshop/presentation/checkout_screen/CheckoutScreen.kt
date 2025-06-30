package com.schoolproject.droidshop.presentation.checkout_screen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.schoolproject.droidshop.domain.model.Cart
import com.schoolproject.droidshop.presentation.cart_screen.CartViewModel
import com.schoolproject.droidshop.ui.theme.poppinsFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    viewModel: CartViewModel = hiltViewModel(),
    navigateToCartScreen: () -> Unit,
    navigateToSuccessScreen: () -> Unit,
) {
    val checkedItems by viewModel.selectedCarts.collectAsStateWithLifecycle()
    val totalPrice by viewModel.totalPrice.collectAsStateWithLifecycle()

    var address by remember { mutableStateOf("") }
    var paymentMethod by remember { mutableStateOf("") }

    val openAddressSheet = remember { mutableStateOf(false) }
    val openPaymentSheet = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .statusBarsPadding()
                ,
                title = {
                    Text(
                        text = "Checkout",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        fontFamily = poppinsFontFamily
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "back",
                        modifier = Modifier
                            .clickable {
                                navigateToCartScreen()
                            }
                    )
                }
            )
        }
    ) { padding ->

        Column(modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)) {

            Text(
                text = "Your Selected Items",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold
            )
            checkedItems.data?.forEach { item ->

                CartItemSelected(item)
//                Row(
//                    Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 8.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Text("${item.productName} x${item.productQuantity}")
//                    Text("$${"%.2f".format(item.productQuantity * item.productPrice)}")
//                }
            }

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Shipping Address",
                fontSize = 16.sp,
                fontFamily = poppinsFontFamily
            )
            Spacer(Modifier.height(8.dp))

            Text(
                text = address,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                fontSize = 32.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold
            )

            Card(
                border = BorderStroke(
                    width = 1.dp,
                    color = Color.LightGray
                ),
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth()
                    .clickable { openAddressSheet.value = true }
                    .wrapContentHeight()
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.Center),
                        text = "Add Address",
                        fontSize = 14.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Medium
                    )
                }
            }



            // 💳 Payment Method
            Text(
                text = "Payment Method",
                fontSize = 16.sp,
                fontFamily = poppinsFontFamily
            )
            Spacer(Modifier.height(8.dp))

            Text(
                text = paymentMethod,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                fontSize = 32.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold
            )

            Card(
                border = BorderStroke(
                    width = 1.dp,
                    color = Color.LightGray
                ),
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth()
                    .clickable { openPaymentSheet.value = true }
                    .wrapContentHeight()
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.Center),
                        text = "Choose Payment Method",
                        fontSize = 14.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // 💰 Total
            Text(
                text = "Total: $${"%.2f".format(totalPrice)}",
                fontSize = 20.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    if (address.isNotBlank() && paymentMethod.isNotBlank()) {

                        viewModel.uploadOrder(
                            address = address,
                            paymentMethod = paymentMethod,
                            onSuccess = {
                                navigateToSuccessScreen()
                            }
                        )
                    }
                },
                enabled = address.isNotBlank() && paymentMethod.isNotBlank(),
                modifier = Modifier
                    .height(55.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Confirm & Pay")
            }
        }
    }

    if (openAddressSheet.value) {
        AddressBottomSheet(
            onDismiss = { openAddressSheet.value = false },
            onAddressEntered = {
                address = it
                openAddressSheet.value = false
            }
        )
    }

    if (openPaymentSheet.value) {
        PaymentBottomSheet(
            onDismiss = { openPaymentSheet.value = false },
            onPaymentSelected = {
                paymentMethod = it
                openPaymentSheet.value = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressBottomSheet(
    onDismiss: () -> Unit,
    onAddressEntered: (String) -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        var text by remember { mutableStateOf("") }

        Column(
            Modifier
                .padding(16.dp)
                .wrapContentHeight()
        ) {
            Text("Enter your address", fontWeight = FontWeight.Bold)
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Address") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(10.dp),
                onClick = { onAddressEntered(text) },
            ) {
                Text(
                    text="Save Address",
                    fontSize = 12.sp,
                    fontFamily = poppinsFontFamily
                )
            }
        }
    }
}


@Composable
fun CartItemSelected(
    cart: Cart
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(80.dp)
    ){
        AsyncImage(
            modifier = Modifier
                .width(80.dp)
            ,
            model = cart.productImage,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.padding(16.dp)
        ){
            Text(
                text = "${cart.productName} x${cart.productQuantity}",
                fontFamily = poppinsFontFamily,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "$${"%.2f".format(cart.productQuantity * cart.productPrice)}",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                fontFamily = poppinsFontFamily
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentBottomSheet(
    onDismiss: () -> Unit,
    onPaymentSelected: (String) -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = "Select Payment Method",
                fontWeight = FontWeight.Bold,
                fontFamily = poppinsFontFamily
            )
            val methods = listOf("Cash on Delivery", "UPI", "Credit Card")

            methods.forEach { method ->
                Text(
                    text = method,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onPaymentSelected(method) }
                        .padding(vertical = 12.dp),
                    fontSize = 16.sp,
                    fontFamily = poppinsFontFamily
                )
            }
        }
    }
}


