# Swag Labs Mobile UI Automation (Android)

UI automation project for the **Swag Labs** React Native app using **UI Automator 2** (instrumentation tests) with **Kotlin** and **JUnit4**.  
The framework follows **Page Object Model (POM)** and includes utilities for **waits** and **scroll-until-found** interactions to reduce flaky tests.

---

## Tech Stack

- Kotlin
- Android Instrumentation Tests (`androidTest`)
- UI Automator 2 (`UiDevice`, `BySelector`, `UiObject2`, `UiScrollable`)
- JUnit4

---

## Prerequisites

- Android Studio (or Gradle CLI)
- Android SDK + Platform Tools
- Emulator or physical device with USB debugging enabled
- Swag Labs app installed on the target device/emulator

> Tip: Use a consistent emulator image/version across your team to reduce UI timing differences.


---
## Assumptions
Assumptions

- The Swag Labs Android app is installed and can be launched on the target emulator/device.
- Tests start from a known app state where the Products screen is reachable (e.g., already authenticated, or authentication is handled before validations begin).
- The app runs in English and UI text is stable, since key locators rely on By.text(...) such as "PRODUCTS", "ADD TO CART", and product names like "Sauce Labs Backpack", "Sauce Labs Bike Light", and "Sauce Labs Onesie".
- The product catalog contains the expected items and their display names do not change (no A/B text changes or localization differences).
- The Products screen supports scrolling and the framework’s scrollToAndClick(...) can bring off-screen products into view.
- On the Cart screen, the REMOVE button is discoverable within the same parent container as the product name (the removal flow depends on locating the product node, then finding REMOVE from its parent).
- No system dialogs (permissions, updates, overlays) interrupt the UI flow during execution.
- Tests are executed in portrait with a supported resolution where the UI layout matches the expected hierarchy.
- Device performance is reasonably stable (timeouts in WaitUtils are sufficient for the emulator/device used in local runs and CI).


---

## Project Structure (Recommended)
Design Overview (Framework Architecture)
This project is a Kotlin + UI Automator 2 instrumentation test framework designed around Page Object Model (POM) to keep tests readable, reusable, and stable as the app UI evolves.
Architecture at a glance

Tests layer (tests/): Defines business scenarios (e.g., add to cart, remove item, checkout). Tests should read like user workflows and avoid UI details.
Page Objects layer (pages/): Encapsulates screen-specific behaviors (e.g., selectProduct(name), removeItemByName(name)) and hides locators and UI mechanics from tests.
Utilities layer (utils/): Shared, reusable low-level interactions such as explicit waits, scrolling, and element lookup patterns.
Test data layer (data/): Centralized constants/models for products and users (single source of truth; avoids “magic strings” in tests).
Base layer (base/): Common setup, device initialization, and shared page helpers.

Key components and responsibilities


BaseTest

Bootstraps UiDevice and test context.
Handles app lifecycle per test (launch/cleanup) to keep tests independent.
Owns the entry point to page objects (directly or via a manager/factory).


BasePage

Provides shared page-level helpers (e.g., isDisplayed, “find or wait”, safe click patterns).
Ensures consistent behavior across all page objects.


Page Objects (e.g., HomeProductsPage, CartPage)

Represent a single screen and expose user actions + assertions.
Keep selectors private and localized to the page class.
Example behaviors shown in your screenshots:


WaitUtils

Central place for explicit waits using Until.hasObject(...).
Includes scroll-until-found behavior: scroll in controlled attempts and stop immediately once the element is detected (reduces wasted scrolling and flakiness).


PageObjectManager ()

Provides a single, consistent way to create page objects with shared dependencies (device/context).
Helps avoid repeated instantiation logic in tests.


Locator strategy (how elements are identified)

The framework primarily uses UI Automator selectors (e.g., by visible text) for readability and quick iteration.
For long-term stability, the preferred order is:

resource-id (if available)
content-desc / accessibility label
text


Data-driven testing approach

Product names, users, and reusable inputs live under data/ and are referenced by tests/pages.
This reduces duplication and makes updates painless (change once; tests stay intact).

End-to-end flow example (how a test typically runs)

BaseTest launches the app and prepares UiDevice.
Test calls a page behavior (e.g., select product by test data name).
Page object uses WaitUtils to wait/scroll until the element is available.
Test asserts outcomes using page-level methods (e.g., item is present/removed in cart).

### Where should test data live?
Place test data in:
- `app/src/androidTest/kotlin/.../data`

This keeps it **out of production (`main`) code** and close to instrumentation tests.

---

## Setup

1. Clone the repo.
2. Open in Android Studio.
3. Ensure an emulator/device is available and visible via ADB.

## Running Tests

## Run all instrumentation tests
./gradlew connectedAndroidTest

## Run a single test class
./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.jparedes.checkoutautomation.tests.CheckoutFlowTest

## Run a single test method
./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.jparedes.checkoutautomation.tests.CheckoutFlowTest#testCompleteCheckoutFlow


