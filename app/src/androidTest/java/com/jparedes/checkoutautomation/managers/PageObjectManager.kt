package com.jparedes.checkoutautomation.managers

import android.content.Context
import androidx.test.uiautomator.UiDevice
import com.jparedes.checkoutautomation.pages.CartPage
import com.jparedes.checkoutautomation.pages.CheckoutCompletePage
import com.jparedes.checkoutautomation.pages.CheckoutInfoPage
import com.jparedes.checkoutautomation.pages.CheckoutOverViewPage
import com.jparedes.checkoutautomation.pages.HomeProductsPage
import com.jparedes.checkoutautomation.pages.LoginPage
import com.jparedes.checkoutautomation.pages.ProductPage

class PageObjectManager(device: UiDevice, context: Context) {
    val loginPage by lazy { LoginPage(device, context) }
    val homeProductsPage by lazy { HomeProductsPage(device, context) }
    val productPage by lazy { ProductPage(device, context) }
    val cartPage by lazy { CartPage(device, context) }
    val checkoutInfoPage by lazy { CheckoutInfoPage(device, context) }
    val checkoutOverViewPage by lazy { CheckoutOverViewPage(device, context) }
    val checkoutCompletePage by lazy { CheckoutCompletePage(device, context) }
}