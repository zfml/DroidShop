package com.schoolproject.droidshop.domain.repository

import com.schoolproject.droidshop.data.model.ProductDto

interface DroidShopRepository {

    suspend  fun getAllProducts(): List<ProductDto>
}