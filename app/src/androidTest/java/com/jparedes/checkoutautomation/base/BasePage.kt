package com.jparedes.checkoutautomation.base

import androidx.test.uiautomator.By
import androidx.test.uiautomator.BySelector
import androidx.test.uiautomator.UiDevice
import com.jparedes.checkoutautomation.utils.WaitUtils

abstract class BasePage(protected val device: UiDevice) {

    protected fun click(selector: BySelector) {
        val obj = WaitUtils.waitForObject(device, selector)
        obj.click()
    }

    protected fun typeText(selector: BySelector, string: String) {
        val obj = WaitUtils.waitForObject(device, selector)
        obj.text = string
    }

    protected fun isDisplayed(selector: BySelector): Boolean {
        return device.hasObject(selector)
    }

    protected fun scrollToAndClick(selector: BySelector) {
        val obj = WaitUtils.waitForObjectWithScroll(device, selector)
        obj.click()
    }

    protected fun scrollToAndClickChildButton(
        itemText: String,
        selector: BySelector
    ) {
        val obj = WaitUtils.waitForObjectWithScroll(device, By.text(itemText))

        val itemTitle = obj.findObject(By.text(itemText))
            ?: throw AssertionError("Item '$itemText' not found on screen after scroll.")

        val itemCard = itemTitle.parent
            ?: throw AssertionError("Could not find parent view for item: $itemText")

        val button = itemCard.findObject(selector)
            ?: throw AssertionError("Could not find '$selector' for item: $itemText")

        button.click()
    }
}