package com.schoolproject.droidshop.presentation.favourite_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.schoolproject.droidshop.domain.model.Favourite
import com.schoolproject.droidshop.domain.repository.FavouriteRepository
import com.schoolproject.droidshop.presentation.navigation.DetailScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val favouriteRepository: FavouriteRepository,
    savedStateHandle: SavedStateHandle,

    ) : ViewModel() {




    // Expose all favourites as StateFlow
    private val _favourites = MutableStateFlow<List<Favourite>>(emptyList())
    val favourites: StateFlow<List<Favourite>> = _favourites.asStateFlow()

    // For tracking single product's favourite status
    private val _isProductFavourited = MutableStateFlow<Boolean?>(null)
    val isProductFavourited: StateFlow<Boolean?> = _isProductFavourited.asStateFlow()

    init {
        observeAllFavourites()
    }

    private fun observeAllFavourites() {
        viewModelScope.launch {
            favouriteRepository.getAllFavourites()
                .collect { favList ->
                    _favourites.value = favList
                }
        }
    }

    fun removeFavouriteById(id: Int) {
        viewModelScope.launch {
            favouriteRepository.removeFromFavouritesById(id)
        }
    }
}