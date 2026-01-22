package com.jparedes.checkoutautomation.pages

import android.content.Context
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import com.jparedes.checkoutautomation.base.BasePage
import com.jparedes.checkoutautomation.utils.WaitUtils

class CheckoutCompletePage (device: UiDevice, context: Context) : BasePage(device) {
    private val checkoutCompletePageTitle = By.text("CHECKOUT: COMPLETE!")
    private val messageTitle = By.text("THANK YOU FOR YOU ORDER")
    private val messageSubtitle = By.text("Your order has been dispatched, and will arrive just as fast as the pony can get there!")
    private val backToHomeBtn = By.text("BACK HOME")

    fun isDisplayed(): Boolean {
        WaitUtils.waitForObject(device, checkoutCompletePageTitle)
        return isDisplayed(checkoutCompletePageTitle)
    }

    fun backToHome() {
        click(backToHomeBtn)
    }

    fun verifyOrderCompleteMessage() {
        WaitUtils.waitForObject(device,messageTitle)
        WaitUtils.waitForObject(device,messageSubtitle)
    }
}