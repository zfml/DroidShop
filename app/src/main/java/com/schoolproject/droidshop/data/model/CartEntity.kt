package com.schoolproject.droidshop.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Cart")
data class CartEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val productId: String,
    val productName: String,
    val productDescription: String,
    val productImage: String,
    val productQuantity: Int
)
