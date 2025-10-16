package com.happybank.app

import android.app.Application
import com.happybank.app.mock.MockServer
import javax.inject.Inject

/**
 * Mock flavor specific initialization
 * Starts NanoHTTPD mock server on app start
 */
class ApplicationInitializer @Inject constructor(
    private val mockServer: MockServer
) {

    fun initialize(application: Application) {
        mockServer.startServer()

        // Register lifecycle callback to stop server on app termination
        application.registerActivityLifecycleCallbacks(
            object : Application.ActivityLifecycleCallbacks {
                override fun onActivityCreated(activity: android.app.Activity, savedInstanceState: android.os.Bundle?) {}
                override fun onActivityStarted(activity: android.app.Activity) {}
                override fun onActivityResumed(activity: android.app.Activity) {}
                override fun onActivityPaused(activity: android.app.Activity) {}
                override fun onActivityStopped(activity: android.app.Activity) {}
                override fun onActivitySaveInstanceState(activity: android.app.Activity, outState: android.os.Bundle) {}
                override fun onActivityDestroyed(activity: android.app.Activity) {
                    // Stop server when last activity is destroyed
                    mockServer.stopServer()
                }
            }
        )
    }
}
