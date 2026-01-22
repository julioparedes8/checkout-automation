package com.jparedes.checkoutautomation.data

data class Product(
    val name: String,
    val price: Double,
    val description: String? = null
)