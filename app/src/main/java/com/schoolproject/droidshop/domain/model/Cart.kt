package com.schoolproject.droidshop.domain.model

data class Cart(
    val id: Int = 0,
    val productId: String,
    val productName: String,
    val productDescription: String,
    val productImage: String,
    val productQuantity: Int,
    val productPrice: Double,
    val isChecked: Boolean
)
