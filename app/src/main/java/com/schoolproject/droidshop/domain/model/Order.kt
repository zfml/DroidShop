package com.schoolproject.droidshop.domain.model

data class Order(
    val userId: String = "",
    val items: List<Cart>,
    val address: String,
    val paymentMethod: String,
    val totalAmount: Double,
    val timestamp: Long = System.currentTimeMillis()
)
