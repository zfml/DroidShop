package com.schoolproject.droidshop.presentation.products.all

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.schoolproject.droidshop.core.util.Resource
import com.schoolproject.droidshop.domain.model.Product
import com.schoolproject.droidshop.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllProductViewModel @Inject constructor(
    private val repository: ProductRepository
): ViewModel(){

    private val _productState = MutableStateFlow<Resource<List<Product>>>(Resource.Loading())
    val productState: StateFlow<Resource<List<Product>>> = _productState

    init {
        fetchAllProducts()
    }

    private fun fetchAllProducts() {
        viewModelScope.launch {
            repository.getAllProducts().collect {
                _productState.value = it
            }
        }
    }

    fun fetchProductsByCategory(categoryId: String) {
        viewModelScope.launch {
            repository.getProductsByCategory(categoryId).collect {
                _productState.value = it
            }
        }
    }
}