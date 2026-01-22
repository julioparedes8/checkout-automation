package com.jparedes.checkoutautomation.utils

import androidx.test.uiautomator.BySelector
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.UiScrollable
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until

object WaitUtils {
    private const val DEFAULT_TIMEOUT = 10_000L

    fun waitForObject(
        device: UiDevice,
        selector: BySelector,
        timeout: Long = DEFAULT_TIMEOUT
    ): UiObject2 {
        if (!device.wait(Until.hasObject(selector), timeout)) {
            throw AssertionError("Object not found: $selector after ${timeout}ms")
        }
        return device.findObject(selector)
            ?: throw AssertionError("Object found by wait but findObject() returned null for: $selector")
    }

    fun waitForObjectWithScroll(
        device: UiDevice,
        selector: BySelector,
        timeout: Long = DEFAULT_TIMEOUT,
        maxScrollAttempts: Int = 10
    ): UiObject2 {
        // First, quick check without scrolling
        if (device.wait(Until.hasObject(selector), timeout / 4)) {
            return device.findObject(selector)
                ?: throw AssertionError("Object found by wait but findObject() returned null for: $selector")
        }

        val scrollable = UiScrollable(UiSelector().scrollable(true)).setAsVerticalList()
        var attempts = 0
        val perScrollTimeout = timeout / (maxScrollAttempts + 1)  // Spread overall timeout across scrolls

        while (attempts < maxScrollAttempts) {
            // Scroll forward one "page"
            try {
                scrollable.scrollForward()
            } catch (e: Exception) {
                break // Can't scroll further; stop trying
            }

            // Short wait after each scroll to let UI settle
            if (device.wait(Until.hasObject(selector), perScrollTimeout)) {
                return device.findObject(selector)
                    ?: throw AssertionError("Object found by wait but findObject() returned null for: $selector")
            }

            attempts++
        }

        throw AssertionError("Object not found (after scrolling $attempts attempts): $selector after ${timeout}ms")
    }

    fun waitForObjectToDisappear(
        device: UiDevice,
        selector: BySelector,
        timeout: Long = DEFAULT_TIMEOUT
    ) {
        val disappeared = device.wait(Until.gone(selector), timeout)
        if (!disappeared) {
            throw AssertionError("Element matching $selector did NOT disappear within $timeout ms.")
        }
    }
}