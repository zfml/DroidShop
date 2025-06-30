package com.schoolproject.droidshop.data.model

data class CartDto(
    val productId: String,
    val productName: String,
    val productDescription: String,
    val productImage: String,
    val productQuantity: Int,
    val productPrice: Double,
    val isChecked: Boolean
)
