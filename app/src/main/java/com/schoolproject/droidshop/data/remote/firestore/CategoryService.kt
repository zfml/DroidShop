package com.schoolproject.droidshop.data.remote.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.schoolproject.droidshop.data.model.CategoryDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CategoryService @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    fun fetchCategories(): Flow<Result<List<CategoryDto>>> = flow {
        try {
            val snapshot = firestore.collection("categories").get().await()
            val list = snapshot.documents.map { it.toObject(CategoryDto::class.java)!! }
            emit(Result.success(list))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}