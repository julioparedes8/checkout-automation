package com.jparedes.checkoutautomation.pages

import android.content.Context
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import com.jparedes.checkoutautomation.base.BasePage
import com.jparedes.checkoutautomation.utils.WaitUtils
import java.math.BigDecimal
import java.math.RoundingMode

class CheckoutOverViewPage (device: UiDevice, context: Context) : BasePage(device) {
    private val checkoutOverviewPageTitle = By.text("CHECKOUT: OVERVIEW")
    private val paymentInfo = By.text("Payment Information:")
    private val shippingInfo = By.text("Shipping Information:")
    private val finishBtn = By.text("FINISH")
    private val itemTotalPrefix = "Item total:"
    private val taxPrefix = "Tax:"
    private val totalPrefix = "Total:"
    private val rate = BigDecimal("0.08")

    fun isDisplayed(): Boolean {
        WaitUtils.waitForObject(device, checkoutOverviewPageTitle)
        return isDisplayed(checkoutOverviewPageTitle)
    }

    fun clickFinish() {
        scrollToAndClick(finishBtn)
    }

    fun isProductOverViewDisplayed(product: String, price: String): Boolean {
        WaitUtils.waitForObjectWithScroll(device, By.text(product))
        WaitUtils.waitForObjectWithScroll(device, By.text(price))
        return isDisplayed(By.text(product)) && isDisplayed(By.text(price))
    }

    fun isPaymentInfoDisplayed(info: String): Boolean {
        WaitUtils.waitForObject(device, paymentInfo)
        WaitUtils.waitForObjectWithScroll(device, By.text(info))
        return isDisplayed(By.text(info))
    }

    fun isShippingInfoDisplayed(info: String): Boolean {
        WaitUtils.waitForObject(device, shippingInfo)
        WaitUtils.waitForObjectWithScroll(device, By.text(info))
        return isDisplayed(By.text(info))
    }

    fun readTotals(): Totals {
        val itemTotalText = WaitUtils.getTextStartsWithScrolling(device, itemTotalPrefix)
        val taxText = WaitUtils.getTextStartsWithScrolling(device, taxPrefix)
        val totalText = WaitUtils.getTextStartsWithScrolling(device, totalPrefix)

        return Totals(
            itemTotal = parseMoney(itemTotalText).money2(),
            tax = parseMoney(taxText).money2(),
            total = parseMoney(totalText).money2()
        )
    }

    fun calculateExpectedTotals(itemTotal: BigDecimal): Pair<BigDecimal, BigDecimal> {
        val expectedTax = itemTotal.multiply(rate).money2()
        val expectedTotal = itemTotal.add(expectedTax).money2()
        return Pair(expectedTax, expectedTotal)
    }

    data class Totals(
        val itemTotal: BigDecimal,
        val tax: BigDecimal,
        val total: BigDecimal
    )

    fun parseMoney(text: String): BigDecimal {
        // Extract first number (supports commas + decimals). Example: "Tax: $2.40" -> 2.40
        val m = Regex("""[-+]?\d{1,3}(?:,\d{3})*(?:\.\d+)?|[-+]?\d+(?:\.\d+)?""")
            .find(text) ?: error("No amount found in: $text")
        return m.value.replace(",", "").toBigDecimal()
    }

    private fun BigDecimal.money2(): BigDecimal = setScale(2, RoundingMode.HALF_UP)
}