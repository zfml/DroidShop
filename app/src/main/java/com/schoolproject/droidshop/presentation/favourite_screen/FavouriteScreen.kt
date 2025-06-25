package com.schoolproject.droidshop.presentation.favourite_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.schoolproject.droidshop.R
import com.schoolproject.droidshop.domain.model.Favourite
import com.schoolproject.droidshop.ui.theme.poppinsFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteScreen(
    viewModel: FavouriteViewModel = hiltViewModel()
){


    val snackbarHostState = remember { SnackbarHostState() }


    val favouriteState by viewModel.favourites.collectAsStateWithLifecycle()

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .statusBarsPadding(),
                title = {
                    Text(
                        text = stringResource(R.string.favourite)
                    )
                }
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                if (favouriteState.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                    ) {
                        Text(
                            text = "No Favourite"
                        )
                    }
                } else {
                    FavouriteContent(
                        onClick = {
                            viewModel.removeFavouriteById(it)
                        },
                        list = favouriteState,
                    )
                }
            }
        }
    )
}

@Composable
fun FavouriteContent(
    modifier: Modifier = Modifier,
    list: List<Favourite>,
    onClick: (Int) -> Unit
) {

    LazyColumn (

    ){
        items(list) {
            FavouriteItem(
                it,
                onClick = onClick
            )
        }
    }

}


@Composable
fun FavouriteItem(
    favourite: Favourite,
    onClick: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(100.dp)
            .background(
                MaterialTheme.colorScheme.background
            )
    ) {
        AsyncImage(
            modifier = Modifier
                .size(
                    height = 100.dp,
                    width = 100.dp
                ),
            model = favourite.productImage,
            contentDescription = favourite.productName,
            contentScale = ContentScale.Crop

        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
                .weight(1f)
        ) {
            Text(
                text = favourite.productName,
                fontFamily = poppinsFontFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 2
            )
            Text(
                text = "$${favourite.productPrice}",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium
            )
        }


        Icon(
            painter = painterResource(R.drawable.icon_favourite_filled_red),
            contentDescription = null,
            modifier = Modifier
                .padding(
                    end = 8.dp,
                    bottom = 4.dp
                )
                .clickable {
                    onClick(favourite.id)
                }
                .align(Alignment.Bottom)
        )

    }
}

@Preview
@Composable
fun FavouriteItemPreview() {
    FavouriteItem(
        favourite = Favourite(
            id = 1,
            productId = "343",
            productName = "Mac Book",
            productPrice = "324",
            productImage = "",
            productCategory = "",
            productQuantity = 3,
        ),
        onClick = {}
    )
}