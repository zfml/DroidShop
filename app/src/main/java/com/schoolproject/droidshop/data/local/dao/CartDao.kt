package com.schoolproject.droidshop.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.schoolproject.droidshop.data.model.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart WHERE productId = :productId LIMIT 1 ")
    suspend fun getCartItemByProductId(productId: String): CartEntity?


    @Query("SELECT * FROM cart WHERE id = :id LIMIT 1 ")
    suspend fun getCartItemById(id: Int): CartEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cartEntity: CartEntity)

    @Query("SELECT * FROM cart ORDER BY id DESC")
    fun getAllCart(): Flow<List<CartEntity>>


    @Query("UPDATE cart SET productQuantity = :quantity WHERE id= :cartId")
    suspend fun updateQuantity(cartId: Int, quantity: Int)

    @Query("DELETE FROM cart WHERE id =:id")
    suspend fun deleteCartById(id: Int)



    @Query("SELECT * FROM Cart WHERE isChecked = 1")
    fun getCheckedCarts(): Flow<List<CartEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCarts(carts: List<CartEntity>)

    @Update
    suspend fun updateCart(cart: CartEntity)

    @Query("UPDATE Cart SET isChecked = :checked")
    suspend fun updateAllChecked(checked: Boolean)




    @Query("DELETE FROM cart")
    suspend fun deleteAllItems()


}