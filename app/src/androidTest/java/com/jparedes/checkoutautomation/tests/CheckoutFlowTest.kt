package com.jparedes.checkoutautomation.tests

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import com.jparedes.checkoutautomation.base.BaseTest
import com.jparedes.checkoutautomation.data.TestProducts.Backpack
import com.jparedes.checkoutautomation.data.TestProducts.Onesie
import com.jparedes.checkoutautomation.data.TestUsers.standardUser
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CheckoutFlowTest : BaseTest() {

    // Add test case ID - Name
    @Test
    fun testCompleteCheckoutFlow() {
        // Login
        pages.loginPage.isDisplayed()
        pages.loginPage.loginAs(standardUser.userName, standardUser.password)

        // Add products
        pages.homeProductsPage.isDisplayed()
        pages.homeProductsPage.addToBagOnProduct(Backpack.name)
        pages.homeProductsPage.selectProduct(Onesie.name)
        pages.productPage.isDisplayed()
        pages.productPage.clickAddToCart()
        pages.productPage.clickBackToProducts()
        pages.homeProductsPage.isDisplayed()

        // Cart operations
        pages.homeProductsPage.clickCart()
        pages.cartPage.isDisplayed()
        pages.cartPage.verifyProductExists(By.text(Backpack.name))
        pages.cartPage.verifyProductExists(By.text(Onesie.name))
        pages.cartPage.removeItemByName(Backpack.name)
        pages.cartPage.verifyProductDoesNotExists(By.text(Backpack.name))

        // Checkout flow
        pages.cartPage.clickCheckoutBtn()
        pages.checkoutInfoPage.isDisplayed()
        pages.checkoutInfoPage.checkoutHappyPath("Julio", "Paredes", "85302")
        pages.checkoutOverViewPage.isDisplayed()
        pages.checkoutOverViewPage.clickFinish()
        pages.checkoutCompletePage.isDisplayed()
        pages.checkoutCompletePage.verifyOrderCompleteMessage()
        pages.checkoutCompletePage.backToHome()
        pages.homeProductsPage.isDisplayed()
    }
}