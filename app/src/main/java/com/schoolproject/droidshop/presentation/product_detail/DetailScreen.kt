package com.schoolproject.droidshop.presentation.product_detail

import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.schoolproject.droidshop.R
import com.schoolproject.droidshop.core.util.Resource
import com.schoolproject.droidshop.domain.model.Favourite
import com.schoolproject.droidshop.domain.model.Product
import com.schoolproject.droidshop.presentation.favourite_screen.FavouriteViewModel
import com.schoolproject.droidshop.ui.theme.poppinsFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    navigateToMainScreen: () -> Unit
){

    val snackbarHostState = remember { SnackbarHostState() }


    val state by viewModel.productState.collectAsStateWithLifecycle()

    val isFavourite by viewModel.isProductFavourited.collectAsStateWithLifecycle()







    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                ,
                title = {
                    Text(
                        text = stringResource(R.string.product_detail),
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        tint = Color.Black,
                        contentDescription = "Back",
                        modifier = Modifier
                            .clickable {
                                navigateToMainScreen()
                            }
                    )
                },
                actions = {
                    when(isFavourite) {
                        true -> {
                            Icon(
                                painter = painterResource(R.drawable.icon_favourite_filled_red),
                                contentDescription = "favourite",
                                modifier = Modifier
                                    .clickable {
                                        viewModel.removeFavouriteById()
                                    },
                                tint = Color.Unspecified,
                            )
                        }
                        false -> {
                            Icon(
                                painter = painterResource(R.drawable.icon_favourite_outlined),
                                contentDescription = "favourite",
                                modifier = Modifier
                                    .clickable {

                                        when(state) {
                                            is Resource.Error -> {}
                                            is Resource.Idle -> {}
                                            is Resource.Loading -> {}
                                            is Resource.Success -> {
                                                state.data?.let {
                                                    Favourite(
                                                        productId = it.id,
                                                        productName = it.name,
                                                        productPrice = it.price.toString(),
                                                        productImage = it.imageUrl,
                                                        productCategory = it.categoryId,
                                                        productQuantity = 1
                                                    )
                                                }?.let {
                                                    viewModel.addFavourite(
                                                        it
                                                    )
                                                }
                                            }
                                        }

                                    },
                                tint = Color.Unspecified,
                            )

                        }
                    }






                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )

            )
        },
        content = { paddingValues ->

            var isLoading by remember { mutableStateOf(true) }

            when(state) {
                is Resource.Error -> {

                    Text(state.message.toString())
                    isLoading = false
                }
                is Resource.Idle -> {
                    isLoading = true
                }
                is Resource.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is Resource.Success -> {
                    DetailContent(
                        product = state.data?: Product(),
                        addToCard = {},
                        paddingValues = paddingValues,
                        isLoading = isLoading
                    )
                }
            }

        }
    )

}

@Composable
fun DetailContent(
    isLoading: Boolean,
    product: Product,
    addToCard: () -> Unit,
    paddingValues: PaddingValues,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                AsyncImage(
                    model = product.imageUrl,
                    contentScale = ContentScale.Crop,
                    contentDescription = product.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
                Text(
                    text = product.name,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 22.sp
                )

                Text(
                    text = product.description,
                    fontFamily = poppinsFontFamily,
                    fontSize = 16.sp
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .height(85.dp)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                HorizontalDivider()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .weight(1f)
                        ,
                        text = "$${product.price}",
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 40.sp

                    )

                    Button(
                        modifier = Modifier
                            .height(55.dp)
                            .width(170.dp),
                        shape = RoundedCornerShape(10.dp),
                        onClick = addToCard,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Row (
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Text(
                                text = stringResource(R.string.add_to_cart),
                                color = Color.White,
                                fontSize = 14.sp,
                            )
                            Spacer(Modifier.size(4.dp))
                            Icon(
                                painter = painterResource(R.drawable.icon_cart_outlined),
                                contentDescription = "Cart",
                                tint = MaterialTheme.colorScheme.background
                            )
                        }
                    }


                }

        }

    }

}



