package com.schoolproject.droidshop.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite")
data class FavouriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val productId: String,
    val productName: String,
    val productPrice: String,
    val productImage: String,
    val productCategory: String,
    val productQuantity: Int
)
