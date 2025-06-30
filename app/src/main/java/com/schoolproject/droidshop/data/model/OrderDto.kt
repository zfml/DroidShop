package com.schoolproject.droidshop.data.model

import com.schoolproject.droidshop.domain.model.Cart

data class OrderDto(
    val userId: String,
    val items: List<CartDto>,
    val address: String,
    val paymentMethod: String,
    val totalAmount: Double,
    val timestamp: Long
)
