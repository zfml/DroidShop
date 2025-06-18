package com.schoolproject.droidshop.domain.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName

data class Category(
    val id: String = "",
    val name: String = "",
    val imageUrl: String = ""
)