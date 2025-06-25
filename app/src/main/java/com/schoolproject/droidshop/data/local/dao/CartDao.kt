package com.schoolproject.droidshop.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.schoolproject.droidshop.data.model.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart WHERE productId = :productId LIMIT 1 ")
    suspend fun getCartItemByProductId(productId: String): CartEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cartEntity: CartEntity)

    @Query("SELECT * FROM cart ORDER BY id DESC")
    fun getAllCart(): Flow<List<CartEntity>>


    @Query("UPDATE cart SET productQuantity = :quantity WHERE id= :cartId")
    suspend fun updateQuantity(cartId: Int, quantity: Int)

    @Query("DELETE FROM cart WHERE id =:id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM cart")
    suspend fun deleteAllItems()


}