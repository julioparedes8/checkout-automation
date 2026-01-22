package com.jparedes.checkoutautomation.pages

import android.content.Context
import androidx.test.uiautomator.By
import androidx.test.uiautomator.BySelector
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
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

    fun verifyProductExists(productName: BySelector) {
        WaitUtils.waitForObject(device, productName)
        isDisplayed(productName)
    }

    fun removeItemByName(productName: String) {
        // Find the cart item node by product name
        val cartItems = device.findObjects(cartItemContent)
        for (item in cartItems) {
            val nameNode = item.findObject(By.text(productName))
            if (nameNode != null) {
                val removeButton = item.findObject(remove)
                if (removeButton != null) {
                    removeButton.click()
                    return
                } else {
                    throw AssertionError("REMOVE button not found in cart item for '$productName'")
                }
            }
        }
        throw AssertionError("Product '$productName' not found in any cart item!")
    }

    fun verifyProductDoesNotExists(productName: BySelector) {
        isNotDisplayed(productName)
    }

    fun clickCheckoutBtn() {
        click(checkoutBtn)
    }
}