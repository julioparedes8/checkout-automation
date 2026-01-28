package com.jparedes.checkoutautomation.tests

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import com.jparedes.checkoutautomation.base.BaseTest
import com.jparedes.checkoutautomation.data.TestProducts.Backpack
import com.jparedes.checkoutautomation.data.TestProducts.Onesie
import com.jparedes.checkoutautomation.data.TestUsers.standardUser
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CheckoutFlowTest : BaseTest() {

    // Add test case ID - Name
    @Test
    fun testCompleteCheckoutFlow() {
        // Login
        assertTrue("Home screen should be displayed", pages.loginPage.isDisplayed())
        pages.loginPage.loginAs(standardUser.userName, standardUser.password)

        // Add products
        assertTrue("Home screen should be displayed", pages.homeProductsPage.isDisplayed())
        pages.homeProductsPage.addToBagOnProduct(Backpack.name)
        pages.homeProductsPage.selectProduct(Onesie.name)
        assertTrue("Products screen should be displayed", pages.productPage.isDisplayed())
        pages.productPage.clickAddToCart()
        pages.productPage.clickBackToProducts()
        assertTrue("Home screen should be displayed", pages.homeProductsPage.isDisplayed())

        // Cart operations
        pages.homeProductsPage.clickCart()
        assertTrue("Cart screen should be displayed", pages.cartPage.isDisplayed())
        assertTrue("Product Should be displayed", pages.cartPage.isProductDisplayed(By.text(Backpack.name)))
        assertTrue("Product Should be displayed", pages.cartPage.isProductDisplayed(By.text(Onesie.name)))
        assertTrue("Product is removed correctly", pages.cartPage.removeItemByName(Backpack.name))
        assertFalse("Removed Product Should not be displayed", pages.cartPage.isProductDisplayed(By.text(Backpack.name)))

        // Checkout flow
        pages.cartPage.clickCheckoutBtn()
        assertTrue("Checkout Info screen should be displayed", pages.checkoutInfoPage.isDisplayed())
        pages.checkoutInfoPage.insertCorrectCheckoutInfo("Julio", "Paredes", "85302")
        assertTrue("Checkout Overview screen should be displayed", pages.checkoutOverViewPage.isDisplayed())
        assertTrue(
            "Product overview should be displayed",
            pages.checkoutOverViewPage.isProductOverViewDisplayed(Onesie.name, "$${Onesie.price}")
        )
        assertTrue("Payment info should be displayed", pages.checkoutOverViewPage.isPaymentInfoDisplayed(standardUser.cardString))
        assertTrue("Shipping info should be displayed", pages.checkoutOverViewPage.isShippingInfoDisplayed(standardUser.shippingAddress))

        val totals = pages.checkoutOverViewPage.readTotals()
        val (expectedTax, expectedTotal) = pages.checkoutOverViewPage.calculateExpectedTotals(totals.itemTotal)

        assertEquals("Item total is not correct", Onesie.price.toBigDecimal(), totals.itemTotal)
        assertEquals("Tax value is not correct", expectedTax, totals.tax)
        assertEquals("Total value is not correct", expectedTotal, totals.total)

        pages.checkoutOverViewPage.clickFinish()
        assertTrue("Checkout Complete screen should be displayed", pages.checkoutCompletePage.isDisplayed())
        assertTrue(
            "Checkout Complete message should be displayed",
            pages.checkoutCompletePage.isConfirmationMessageDisplayed()
        )
        pages.checkoutCompletePage.backToHome()
        assertTrue("Home screen should be displayed", pages.homeProductsPage.isDisplayed())
    }
}