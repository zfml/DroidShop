package com.schoolproject.droidshop.presentation.home_screen


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.schoolproject.droidshop.R
import com.schoolproject.droidshop.presentation.cart_screen.CartViewModel
import com.schoolproject.droidshop.presentation.components.SectionText
import com.schoolproject.droidshop.presentation.components.TabCategory
import com.schoolproject.droidshop.ui.theme.poppinsFontFamily
import kotlinx.coroutines.CoroutineScope

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToProductDetailScreen: (String) -> Unit,
    navigateToCartScreen: () -> Unit,
    cartViewModel: CartViewModel = hiltViewModel(),

){


    val cartItems by cartViewModel.cartItems.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()



            Scaffold(
                snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState) {
                        Snackbar(
                            modifier = Modifier.padding(
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 90.dp

                            )
                        ) {
                            Text(
                                text = it.visuals.message,
                                fontFamily = poppinsFontFamily
                            )
                        }
                    }
                },
                topBar = {
                    CenterAlignedTopAppBar(

                        title = {
                        },
                        navigationIcon = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(start = 8.dp),
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.droid),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(30.dp),
                                )
                                Text(
                                    text = "Droid",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    fontFamily = poppinsFontFamily,
                                    modifier = Modifier
                                        .padding(
                                            start = 8.dp,
                                            top = 8.dp
                                        ),
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = "Shop",
                                    fontSize = 18.sp,
                                    modifier = Modifier
                                        .padding(
                                            start = 4.dp,
                                            top = 8.dp
                                        ),
                                    color = MaterialTheme.colorScheme.primary,
                                    fontFamily = poppinsFontFamily
                                )
                            }
                        },
                        actions = {
                            BadgedBox(
                                badge = {

                                    val items = cartItems
                                    if(items.isNotEmpty()) {
                                        Badge(
                                            containerColor = Color.Red,
                                            contentColor = Color.White,
                                            modifier = Modifier
                                                .offset(
                                                    x = (-10).dp,
                                                    y =(-7).dp
                                                )
                                        ){
                                            Text(items.size.toString())
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.icon_cart_outlined),
                                    contentDescription = "Cart",
                                    modifier = Modifier
                                        .padding(end = 16.dp)
                                        .clickable {
                                            navigateToCartScreen()
                                        }
                                )
                            }
                        }
                    )
                },
                content = { paddingValues ->
                    Column(
                        modifier = modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(paddingValues)


                    ) {
                        ImageSliderSection()

                        Spacer(modifier = Modifier.height(16.dp))
                        CategorySection(
                            title = "Category",
                            content = {
                                TabCategory(
                                    gridHeight = 640.dp,
                                    limit = 4,
                                    navigateToDetail = { productId ->
                                        navigateToProductDetailScreen(productId)
                                    },
                                    snackbarHostState = snackbarHostState,
                                    scope = scope,
                                    modifier = Modifier.padding(8.dp)
                                )
                            },
                            navigateToSeeAll = {}
                        )



                    }
                }
            )





}

@Composable
fun CategorySection(
    title: String,
    title2: String = "See all",
    navigateToSeeAll: () -> Unit,
    content: @Composable () -> Unit
) {
    Column {
        if (title != ""){
           SectionText(
               "Categories",
               text2 = title2

           )
        }
        content()
    }

}