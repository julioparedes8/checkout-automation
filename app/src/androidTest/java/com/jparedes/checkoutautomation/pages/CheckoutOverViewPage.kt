package com.jparedes.checkoutautomation.pages

import android.content.Context
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import com.jparedes.checkoutautomation.base.BasePage
import com.jparedes.checkoutautomation.utils.WaitUtils

class CheckoutOverViewPage (device: UiDevice, context: Context) : BasePage(device) {
    private val checkoutOverviewPageTitle = By.text("CHECKOUT: OVERVIEW")
    private val finishBtn = By.text("FINISH")

    fun isDisplayed(): Boolean {
        WaitUtils.waitForObject(device, checkoutOverviewPageTitle)
        return isDisplayed(checkoutOverviewPageTitle)
    }

    fun clickFinish() {
        scrollToAndClick(finishBtn)
    }
}