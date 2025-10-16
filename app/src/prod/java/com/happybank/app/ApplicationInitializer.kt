package com.happybank.app

import android.app.Application
import javax.inject.Inject

/**
 * Prod flavor specific initialization
 * No mock server needed for production
 */
class ApplicationInitializer @Inject constructor() {

    fun initialize(application: Application) {
        // No initialization needed for prod flavor
    }
}
