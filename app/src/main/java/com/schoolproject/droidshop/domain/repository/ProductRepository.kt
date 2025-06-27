package com.schoolproject.droidshop.domain.repository

import com.schoolproject.droidshop.core.util.Resource
import com.schoolproject.droidshop.domain.model.Cart
import com.schoolproject.droidshop.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getAllProducts(): Flow<Resource<List<Product>>>
    fun getProductsByCategory(categoryId: String): Flow<Resource<List<Product>>>
    fun getProductById(productId: String): Flow<Resource<Product>>

    suspend fun addToCart(cart: Cart):Resource<Unit>

     fun getAllCartItems(): Flow<List<Cart>>

    suspend fun deleteCartById(cartId: Int)

    fun getCheckedCarts(): Flow<Resource<List<Cart>>>

    suspend fun toggleChecked(id: Int)

    suspend fun toggleAllChecked(checked: Boolean)

    suspend fun changeQuantity(id: Int, delta: Int)

}