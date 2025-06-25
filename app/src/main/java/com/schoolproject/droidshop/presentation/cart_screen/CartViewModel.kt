package com.schoolproject.droidshop.presentation.cart_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.schoolproject.droidshop.core.util.Resource
import com.schoolproject.droidshop.domain.model.Cart
import com.schoolproject.droidshop.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val productRepository: ProductRepository
): ViewModel(){

    private val _cartState = MutableStateFlow<Resource<List<Cart>>>(Resource.Loading())
    val cartState: StateFlow<Resource<List<Cart>>> = _cartState

    init {
        getAllCartItems()
    }

    private fun getAllCartItems() {
        viewModelScope.launch {
            productRepository.getAllCartItems()
                .collectLatest {
                    _cartState.value = it
                }
        }
    }

    fun addToCart(cart: Cart) {
        viewModelScope.launch {
            productRepository.addToCart(cart)
        }
    }

}