package com.jparedes.checkoutautomation.pages

import android.content.Context
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import com.jparedes.checkoutautomation.base.BasePage
import com.jparedes.checkoutautomation.utils.WaitUtils

class ProductPage (device: UiDevice, context: Context) : BasePage(device) {
    private val backToProducts = By.text("BACK TO PRODUCTS")
    private val addToCart = By.text("ADD TO CART")

    fun isDisplayed(): Boolean {
        WaitUtils.waitForObject(device, backToProducts)
        return isDisplayed(backToProducts)
    }

    fun clickBackToProducts() {
        click(backToProducts)
    }

    fun clickAddToCart() {
        scrollToAndClick(addToCart)
    }

}