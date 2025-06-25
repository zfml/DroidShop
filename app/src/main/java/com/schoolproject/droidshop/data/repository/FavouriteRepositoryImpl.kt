package com.schoolproject.droidshop.data.repository

import com.schoolproject.droidshop.data.local.dao.FavouriteDao
import com.schoolproject.droidshop.data.mappers.toFavourite
import com.schoolproject.droidshop.data.mappers.toFavouriteEntity
import com.schoolproject.droidshop.domain.model.Favourite
import com.schoolproject.droidshop.domain.repository.FavouriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavouriteRepositoryImpl(
    private val favouriteDao: FavouriteDao
) : FavouriteRepository {

    override suspend fun addToFavourites(favourite: Favourite) {
        favouriteDao.insert(favourite.toFavouriteEntity())
    }

    override fun getAllFavourites(): Flow<List<Favourite>> {
        return favouriteDao.getAllFavourites().map { it.map { it.toFavourite() } }
    }

    override suspend fun removeFromFavouritesById(id: Int) {
        favouriteDao.deleteById(id)
    }

    override fun isProductFavourited(productId: String): Flow<Boolean> {
        return favouriteDao.isProductFavouritedFlow(productId = productId)

    }
}