package com.schoolproject.droidshop.presentation.product_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.schoolproject.droidshop.core.util.Resource
import com.schoolproject.droidshop.domain.model.Favourite
import com.schoolproject.droidshop.domain.model.Product
import com.schoolproject.droidshop.domain.repository.FavouriteRepository
import com.schoolproject.droidshop.domain.repository.ProductRepository
import com.schoolproject.droidshop.presentation.navigation.DetailScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: ProductRepository,
    private val favouriteRepository: FavouriteRepository

): ViewModel() {
     val product = savedStateHandle.toRoute<DetailScreen>()


    private val _favourites = MutableStateFlow<List<Favourite>>(emptyList())
    val favourites: StateFlow<List<Favourite>> = _favourites.asStateFlow()


    private val _productState = MutableStateFlow<Resource<Product>>(Resource.Loading())
    val productState: StateFlow<Resource<Product>> = _productState




    // For tracking single product's favourite status
    private val _isProductFavourited = MutableStateFlow(false)
    val isProductFavourited: StateFlow<Boolean> = _isProductFavourited.asStateFlow()

    init {
        getProductById()
        checkIfProductIsFavourited()
        observeAllFavourites()
    }



    private fun getProductById(){
        viewModelScope.launch {
            repository.getProductById(product.productId).collect {
                when(it) {
                    is Resource.Error -> {
                        _productState.value = Resource.Error(it.message.toString())
                    }
                    is Resource.Idle -> {
                        _productState.value = Resource.Loading()

                    }
                    is Resource.Loading -> {
                        _productState.value = Resource.Loading()
                    }
                    is Resource.Success -> {
                        _productState.value = Resource.Success(it.data)

                    }

                }
            }
        }
    }



    private fun observeAllFavourites() {
        viewModelScope.launch {
            favouriteRepository.getAllFavourites()
                .collect { favList ->
                    _favourites.value = favList
                }
        }
    }




    private fun checkIfProductIsFavourited() {
        viewModelScope.launch {
            favouriteRepository.isProductFavourited(product.productId)
                .collect { isFavourited ->
                    _isProductFavourited.value = isFavourited
                }
        }
    }

    fun addFavourite(favourite: Favourite) {
        viewModelScope.launch {
            favouriteRepository.addToFavourites(favourite)
        }
    }

    fun removeFavouriteById() {

        viewModelScope.launch {

            _favourites.value.find { it.productId == product.productId }?.let {
                favouriteRepository.removeFromFavouritesById(it.id)

            }
        }
    }





}