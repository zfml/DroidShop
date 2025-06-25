package com.schoolproject.droidshop.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.schoolproject.droidshop.core.util.Resource
import com.schoolproject.droidshop.data.local.dao.CartDao
import com.schoolproject.droidshop.data.local.dao.FavouriteDao
import com.schoolproject.droidshop.data.mappers.toCart
import com.schoolproject.droidshop.data.mappers.toCartEntity
import com.schoolproject.droidshop.data.mappers.toProduct
import com.schoolproject.droidshop.data.model.ProductDto
import com.schoolproject.droidshop.domain.model.Cart
import com.schoolproject.droidshop.domain.model.Product
import com.schoolproject.droidshop.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl  @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val cartDao: CartDao,
    private val favouriteDao: FavouriteDao
): ProductRepository{

    private val productsCollection = firestore.collection("products")

    override fun getAllProducts(): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading())
        try {
            val snapshot = productsCollection.get().await()
            val products = snapshot.documents.mapNotNull { doc ->
                doc.toObject(ProductDto::class.java)?.copy(id = doc.id)?.toProduct()
            }
            emit(Resource.Success(products))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
        }
    }

    override fun getProductsByCategory(categoryId: String): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading())
        try {
            val snapshot = productsCollection.whereEqualTo("categoryId", categoryId).get().await()
            val products = snapshot.documents.mapNotNull { doc ->
                doc.toObject(ProductDto::class.java)?.copy(id = doc.id)?.toProduct()
            }
            emit(Resource.Success(products))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Failed to load category products"))
        }
    }

    override fun getProductById(productId: String): Flow<Resource<Product>> = flow {
        emit(Resource.Loading())
        try {
            val doc = productsCollection.document(productId).get().await()
            val product = doc.toObject(ProductDto::class.java)?.copy(id = doc.id)?.toProduct()
            if (product != null) {
                emit(Resource.Success(product))
            } else {
                emit(Resource.Error("Product not found"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Failed to load product detail"))
        }
    }




    override suspend fun addToCart(cart: Cart) =try {

        val existingCartItem = cartDao.getCartItemByProductId(cart.productId)
        if (existingCartItem != null){
            val newQuantity = existingCartItem.productQuantity + cart.productQuantity
            cartDao.updateQuantity(existingCartItem.id, newQuantity)
        } else {
            cartDao.insert(cart.toCartEntity())
        }
        Resource.Success(Unit)
        }catch (e: Exception) {
            Resource.Error(e.localizedMessage)
        }

    override suspend fun getAllCartItems(): Flow<Resource<List<Cart>>> = flow {
        emit(Resource.Loading())
        cartDao.getAllCart()
            .map { cartEntities ->
                val domainModels = cartEntities.map { it.toCart()}
                Resource.Success(domainModels)
            }
            .catch { e ->
                emit(Resource.Error(e.message ?: "Unexpected Error"))
            }
            .collect {
                emit(it)
            }
    }


}