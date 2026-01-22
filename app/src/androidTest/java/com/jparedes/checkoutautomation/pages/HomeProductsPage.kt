package com.jparedes.checkoutautomation.pages

import android.content.Context
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import com.jparedes.checkoutautomation.base.BasePage
import com.jparedes.checkoutautomation.utils.WaitUtils

class HomeProductsPage (device: UiDevice, context: Context) : BasePage(device) {

    private val productsTitle = By.text("PRODUCTS")
    private val addToCartButtonSelector = By.text("ADD TO CART")
    private val cart = By.desc("test-Cart")

    fun isDisplayed(): Boolean {
        WaitUtils.waitForObject(device, productsTitle)
        return isDisplayed(productsTitle)
    }

    fun selectProduct(productName: String) {
        scrollToAndClick(By.text(productName))
    }

    fun addToBagOnProduct(productName: String) {
        scrollToAndClickChildButton(productName,addToCartButtonSelector)
    }

    fun clickCart() {
        click(cart)
    }

}