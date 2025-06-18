package com.schoolproject.droidshop.data.remote.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.schoolproject.droidshop.data.model.ProductDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProductService @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    fun fetchProductsByCategory(categoryId: String): Flow<Result<List<ProductDto>>> = flow {
        try {
            val snapshot = firestore.collection("products")
                .whereEqualTo("categoryId", categoryId)
                .get().await()
            val list = snapshot.documents.map { it.toObject(ProductDto::class.java)!! }
            emit(Result.success(list))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}