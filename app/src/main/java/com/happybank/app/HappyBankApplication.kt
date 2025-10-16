package com.happybank.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class HappyBankApplication : Application() {

    @Inject
    lateinit var applicationInitializer: ApplicationInitializer

    override fun onCreate() {
        super.onCreate()
        applicationInitializer.initialize(this)
    }
}
