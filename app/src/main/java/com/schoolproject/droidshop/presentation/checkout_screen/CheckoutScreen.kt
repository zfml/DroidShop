package com.schoolproject.droidshop.presentation.checkout_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.schoolproject.droidshop.core.util.Resource
import com.schoolproject.droidshop.presentation.cart_screen.CartViewModel

@Composable
fun CheckoutScreen(
    viewModel: CartViewModel = hiltViewModel()
) {

    val state by viewModel.selectedCarts.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {

        },
        content = { paddingValues ->



            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                when(state) {
                    is Resource.Error -> {}
                    is Resource.Idle -> {}
                    is Resource.Loading -> {}
                    is Resource.Success -> {

                        LazyColumn {
                            state.data?.let {
                                items(it) { cart ->
                                    Text(cart.productName)
                                }
                            }

                        }
                    }
                }

            }
        }
    )
}