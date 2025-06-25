package com.schoolproject.droidshop.domain.model

import androidx.room.PrimaryKey

data class Favourite(
    val id: Int = 0,
    val productId: String,
    val productName: String,
    val productPrice: String,
    val productImage: String,
    val productCategory: String,
    val productQuantity: Int
)