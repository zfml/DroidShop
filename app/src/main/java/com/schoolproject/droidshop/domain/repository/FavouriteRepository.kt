package com.schoolproject.droidshop.domain.repository

import com.schoolproject.droidshop.domain.model.Favourite
import kotlinx.coroutines.flow.Flow

interface FavouriteRepository {

    suspend fun addToFavourites(favourite: Favourite)

    fun getAllFavourites(): Flow<List<Favourite>>

    suspend fun removeFromFavouritesById(id: Int)

    fun isProductFavourited(productId: String): Flow<Boolean>
}
