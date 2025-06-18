package com.schoolproject.droidshop.data.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName

data class ProductDto(
    @DocumentId
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val price: Double = 0.0,
    @PropertyName("image_url")
    val imageUrl: String = "",
    @PropertyName("category_id")
    val categoryId: String = ""
)