package com.jparedes.checkoutautomation.data

data class User(
    val userName: String,
    val password: String,
    val cardString: String = "",
    val shippingAddress: String = ""
)
