package com.schoolproject.droidshop.data.repository

import com.schoolproject.droidshop.data.model.ProductDto
import com.schoolproject.droidshop.data.remote.auth.FirebaseAuthService
import com.schoolproject.droidshop.domain.repository.DroidShopRepository
import javax.inject.Inject

class DroidShopRepositoryImpl @Inject constructor(
    val firebaseAuthService: FirebaseAuthService
): DroidShopRepository {
    override suspend fun getAllProducts(): List<ProductDto> {
        return emptyList()
    }
}