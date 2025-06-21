package com.schoolproject.droidshop.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.schoolproject.droidshop.core.util.Resource
import com.schoolproject.droidshop.data.mappers.toProduct
import com.schoolproject.droidshop.data.model.ProductDto
import com.schoolproject.droidshop.domain.model.Product
import com.schoolproject.droidshop.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl  @Inject constructor(
    private val firestore: FirebaseFirestore
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
}