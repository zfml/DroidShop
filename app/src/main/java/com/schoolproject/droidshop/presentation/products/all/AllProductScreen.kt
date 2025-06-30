package com.schoolproject.droidshop.presentation.products.all

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.schoolproject.droidshop.R
import com.schoolproject.droidshop.core.util.Resource
import com.schoolproject.droidshop.domain.model.Cart
import com.schoolproject.droidshop.presentation.cart_screen.CartViewModel
import com.schoolproject.droidshop.ui.theme.poppinsFontFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AllProductScreen(
    gridHeight: Dp = Dp.Unspecified,
    limit: Int,
    height: Dp,
    count: Int = 4,
    navigateToDetail: (String) -> Unit,
    viewModel: AllProductViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope
) {

    val productState by viewModel.productState.collectAsState()

    val context = LocalContext.current



    when (productState) {
        is Resource.Loading ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()

            }
        is Resource.Success -> {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(195.dp),
                modifier = Modifier.heightIn(min = gridHeight, max = gridHeight)
            ) {

                val products = productState.data ?: emptyList()

                productState.data?.let {
                    items(it) { product ->
                        ProductCard(
                            image = product.imageUrl,
                            category = "All",
                            name = product.name,
                            description = product.description,
                            price = product.price.toString(),
                            addToCart = {
                                cartViewModel.addToCart(
                                    Cart(
                                        productId = product.id,
                                        productName = product.name,
                                        productDescription = product.description,
                                        productImage = product.imageUrl,
                                        productPrice = product.price,
                                        productQuantity = 1,
                                        isChecked = false
                                    )
                                )


                                scope.launch {

                                    snackbarHostState
                                        .showSnackbar(
                                            message = "Product added to cart",
                                            duration = SnackbarDuration.Short
                                        )
                                }
                            },
                            navigateToDetail = {
                                navigateToDetail(product.id)
                            }
                        )
                    }
                }


            }
        }
        is Resource.Error -> Text("Error: ${(productState as Resource.Error).message}")
        is Resource.Idle -> {}
    }


}

@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    image: String,
    category: String,
    name: String,
    description: String,
    price: String,
    addToCart: () -> Unit,
    navigateToDetail: () -> Unit
) {
    Column(
        modifier = modifier
            .width(160.dp)
            .padding(16.dp)
            .clickable {
                navigateToDetail()
            }
            .wrapContentHeight(),
        horizontalAlignment = Alignment.Start
    ) {

        AsyncImage(
         model = image,
         contentDescription = null,
         contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(
                    vertical = 8.dp,
                )
                .fillMaxWidth()
                .height(160.dp)
                .clip(RoundedCornerShape(10.dp)),
            onError = {
                Log.d("Error",it.result.toString())
            }
        )

        Column{
            Text(
                fontSize = 14.sp,
                text = category,
                color = MaterialTheme.colorScheme.outline,
                fontFamily = poppinsFontFamily
            )
            Text(
                text = name,
                maxLines = 1,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = poppinsFontFamily
            )


            Row(
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .offset(y = (5).dp)
                        .weight(1f),
                    text = "$$price",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    fontFamily = poppinsFontFamily
                )
                Icon(
                    painter = painterResource(R.drawable.icon_cart_outlined),
                    contentDescription = "Cart",
                    tint = MaterialTheme.colorScheme.background,
                    modifier = Modifier
                        .clickable { addToCart() }
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(6.dp)
                )
            }


        }



    }
}