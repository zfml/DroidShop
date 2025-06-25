package com.schoolproject.droidshop.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.schoolproject.droidshop.data.model.FavouriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favouriteEntity: FavouriteEntity)


    @Query("SELECT * FROM favourite ORDER BY id DESC")
    fun getAllFavourites(): Flow<List<FavouriteEntity>>

    @Query("DELETE FROM favourite WHERE id = :id")
    suspend fun deleteById(id: Int)


    @Query("SELECT EXISTS (SELECT 1 FROM favourite WHERE productId = :productId)")
    fun isProductFavouritedFlow(productId: String): Flow<Boolean>

}