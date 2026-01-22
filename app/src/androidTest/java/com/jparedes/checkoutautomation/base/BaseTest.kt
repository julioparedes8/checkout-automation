package com.jparedes.checkoutautomation.base

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import com.jparedes.checkoutautomation.managers.PageObjectManager
import org.junit.After
import org.junit.Before

open class BaseTest {
    companion object {
        private const val PACKAGE = "com.swaglabsmobileapp"
    }
    protected lateinit var device: UiDevice
    protected lateinit var context: Context
    protected lateinit var pages: PageObjectManager
    private val timeout = 10_000L

    @Before
    fun setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        context = ApplicationProvider.getApplicationContext()
        launchAppWithUiAutomator()
        pages = PageObjectManager(device, context)
    }

    private fun launchAppWithUiAutomator() {
        // Start clean
        device.executeShellCommand("am force-stop $PACKAGE")

        // Black box launch
        val monkeyCmd = "monkey -p $PACKAGE -c android.intent.category.LAUNCHER 1"
        device.executeShellCommand(monkeyCmd)

        //wait to launch
        val launched = device.wait(
            Until.hasObject(By.pkg(PACKAGE)),timeout
        )

        if(!launched){
            throw RuntimeException("Failed to launch app package: $PACKAGE")
        }
    }

    @After
    fun tearDown() {
        // Stop the app to ensure a fresh state for the next run
        device.executeShellCommand("am force-stop $PACKAGE")

        // add further clean up or log collection here
        // e.g., collectLogs(), clearTestData()
    }
}