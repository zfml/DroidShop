package com.schoolproject.droidshop.data.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName

data class CategoryDto(
    @DocumentId
    val id: String = "",
    val name: String = "",
    @PropertyName("image_url")
    val imageUrl: String = ""
)