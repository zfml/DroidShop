package com.schoolproject.droidshop.presentation.cart_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.schoolproject.droidshop.core.util.Resource

@Composable
fun CartScreen(
    viewModel: CartViewModel = hiltViewModel()
){

    val state by viewModel.cartState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {

        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {

                when(state) {
                    is Resource.Error -> {}
                    is Resource.Idle -> {}
                    is Resource.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    is Resource.Success -> {

                        state.data?.let { carts ->
                            if(carts.isNotEmpty()) {
                                LazyColumn {
                                    items(carts) {
                                        Text(it.productName)
                                    }
                                }
                            }
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