package com.jparedes.checkoutautomation.data

object TestUsers {
    val standardUser = User(
        userName = "standard_user",
        password = "secret_sauce",
        cardString = "SauceCard #31337",
        shippingAddress = "FREE PONY EXPRESS DELIVERY!"
    )
    val lockedOutUser = User(
        userName = "locked_out_user",
        password = "secret_sauce"
    )
    val problemUser = User(
        userName = "problemUser",
        password = "secret_sauce"
    )
}