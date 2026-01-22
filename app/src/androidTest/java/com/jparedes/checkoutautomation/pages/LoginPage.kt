package com.jparedes.checkoutautomation.pages

import android.content.Context
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import com.jparedes.checkoutautomation.base.BasePage
import com.jparedes.checkoutautomation.utils.WaitUtils

class LoginPage(device: UiDevice, context: Context) : BasePage(device) {

    private val userNameField = By.hint("Username")
    private val passwordField = By.hint("Password")
    private val loginButton = By.text("LOGIN")
    /*
    * Espresso equivalent
    *     private val userNameField = withId(R.id_user_name)
    *     private val passwordField = withId(R.id_user_password)
    *     private val loginButton = withId(R.id_login_button)
    * */

    fun isDisplayed(): Boolean {
        WaitUtils.waitForObject(device, userNameField)
        return isDisplayed(userNameField)
    }

    private fun enterUsername(username: String) {
        typeText(userNameField, username)
    }

    private fun enterPassword(password: String) {
        typeText(passwordField, password)
    }

    private fun tapLogin() {
        click(loginButton)
    }

    fun loginAs(username: String, password: String) {
        enterUsername(username)
        enterPassword(password)
        tapLogin()
    }
}