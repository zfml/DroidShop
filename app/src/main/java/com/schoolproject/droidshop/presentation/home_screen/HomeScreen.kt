package com.schoolproject.droidshop.presentation.home_screen


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.schoolproject.droidshop.R
import com.schoolproject.droidshop.presentation.components.SectionText
import com.schoolproject.droidshop.presentation.components.TabCategory

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
 modifier: Modifier = Modifier
){

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()




            Scaffold(
                modifier = Modifier.statusBarsPadding(),
                topBar = {
                    CenterAlignedTopAppBar(
                        windowInsets = WindowInsets(0, 0, 0, 0),

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
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        },
                        actions = {
                            BadgedBox(
                                badge = {
                                    if(true) {
                                        Badge(
                                            containerColor = Color.Red,
                                            contentColor = Color.White,
                                            modifier = Modifier
                                                .offset(
                                                    x = (-9).dp,
                                                    y =(-7).dp
                                                )
                                        ){
                                            Text("3")
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
                            .padding(paddingValues)
                            .verticalScroll(rememberScrollState())

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