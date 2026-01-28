package com.jparedes.checkoutautomation.pages

import android.content.Context
import androidx.test.uiautomator.By
import androidx.test.uiautomator.BySelector
import androidx.test.uiautomator.UiDevice
import com.jparedes.checkoutautomation.base.BasePage
import com.jparedes.checkoutautomation.utils.WaitUtils

class CartPage (device: UiDevice, context: Context) : BasePage(device) {
    private val cartTitle = By.text("YOUR CART")
    private val cartItemContent = By.desc("test-Cart Content")
    private val remove = By.text("REMOVE")
    private val checkoutBtn = By.text("CHECKOUT")

    fun isDisplayed(): Boolean {
        WaitUtils.waitForObject(device, cartTitle)
        return isDisplayed(cartTitle)
    }

    fun isProductDisplayed(productName: BySelector): Boolean {
        return WaitUtils.hasObject(device, productName)
    }

    fun removeItemByName(productName: String): Boolean {
        // Find the cart item node by product name
        val cartItems = device.findObjects(cartItemContent)
        for (item in cartItems) {
            val productSelector = By.text(productName)
            val nameNode = item.findObject(productSelector)
            if (nameNode != null) {
                val removeButton = item.findObject(remove)
                if (removeButton != null) {
                    removeButton.click()
                    // Wait for the item to disappear from the screen
                    WaitUtils.waitForObjectToDisappear(device, productSelector)
                    return true
                } else {
                    return false
                }
            }
        }
        return false
    }

    fun clickCheckoutBtn() {
        click(checkoutBtn)
    }
}