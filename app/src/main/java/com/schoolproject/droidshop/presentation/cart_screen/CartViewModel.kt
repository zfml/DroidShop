package com.schoolproject.droidshop.presentation.cart_screen

import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.util.query
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.schoolproject.droidshop.core.util.Resource
import com.schoolproject.droidshop.data.model.CartDto
import com.schoolproject.droidshop.data.model.OrderDto
import com.schoolproject.droidshop.domain.model.Cart
import com.schoolproject.droidshop.domain.model.Order
import com.schoolproject.droidshop.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val productRepository: ProductRepository
): ViewModel(){

    val cartItems: StateFlow<List<Cart>> = productRepository.getAllCartItems()
        .stateIn(viewModelScope,SharingStarted.Eagerly, emptyList())

    val selectedCarts: StateFlow<Resource<List<Cart>>> = productRepository.getCheckedCarts()
        .stateIn(viewModelScope,SharingStarted.WhileSubscribed(5000), Resource.Loading())


    val totalPrice: StateFlow<Double> = cartItems
        .map { carts ->
            carts.filter { it.isChecked }
                .sumOf { it.productPrice * it.productQuantity }
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, 0.0)

    val isCheckoutEnabled: StateFlow<Boolean> = cartItems
        .map { carts -> carts.any { it.isChecked } }
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    val isAllChecked: StateFlow<Boolean> = cartItems
        .map { carts ->
            carts.isNotEmpty() && carts.all { it.isChecked }
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)






    fun removeCart(id: Int) {
        viewModelScope.launch {
            productRepository.deleteCartById(id)
        }
    }

    fun toggleCheckedAllCartItem(isChecked: Boolean) {
        viewModelScope.launch {
            productRepository.toggleAllChecked(isChecked)
        }
    }

    fun toggleChecked(id: Int) {
        viewModelScope.launch {
            productRepository.toggleChecked(id)
        }
    }

    fun increaseQuantity(id: Int) {
        viewModelScope.launch {
            productRepository.changeQuantity(id, delta = 1)
        }
    }

    fun decreaseQuantity(id: Int) {
        viewModelScope.launch {
            productRepository.changeQuantity(id, delta = -1)
        }
    }




    fun addToCart(cart: Cart) {
        viewModelScope.launch {
            productRepository.addToCart(cart)
        }
    }




    fun uploadOrder(
        address: String,
        paymentMethod: String,
        onSuccess: () -> Unit
    ) {

        val checkedItems  =  selectedCarts.value.data ?: emptyList()

        val order = Order(
            address = address,
            paymentMethod = paymentMethod,
            totalAmount = checkedItems.sumOf { it.productPrice * it.productQuantity },
            items = checkedItems,
            timestamp = System.currentTimeMillis(),
        )

        viewModelScope.launch {
           when(productRepository.uploadOrder(order)) {
               is Resource.Error -> {}
               is Resource.Idle -> {}
               is Resource.Loading -> {}
               is Resource.Success -> {
                   onSuccess()
               }
           }

        }
    }
}