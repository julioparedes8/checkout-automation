package com.jparedes.checkoutautomation.pages

import android.content.Context
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import com.jparedes.checkoutautomation.base.BasePage
import com.jparedes.checkoutautomation.utils.WaitUtils

class CheckoutInfoPage (device: UiDevice, context: Context) : BasePage(device) {
    private val checkoutInfoPageTitle = By.text("CHECKOUT: INFORMATION")
    private val firstNameField = By.hint("First Name")
    private val lastNameField = By.hint("Last Name")
    private val zipCodeField = By.hint("Zip/Postal Code")
    private val continueBtn = By.text("CONTINUE")

    fun isDisplayed(): Boolean {
        WaitUtils.waitForObject(device, checkoutInfoPageTitle)
        return isDisplayed(checkoutInfoPageTitle)
    }

    private fun enterFirstName(firstName: String) {
        typeText(firstNameField, firstName)
    }

    private fun enterLastName(lastName: String) {
        typeText(lastNameField, lastName)
    }

    private fun enterPostalCode(zipCode: String) {
        typeText(zipCodeField, zipCode)
    }

    private fun tapContinue() {
        click(continueBtn)
    }

    fun checkoutHappyPath(firstName: String, lastName: String, postalCode: String) {
        enterFirstName(firstName)
        enterLastName(lastName)
        enterPostalCode(postalCode)
        tapContinue()
    }
}