package com.mining.mining.base.util

import android.content.Context
import android.content.res.Resources
import com.mining.mining.mining.App


object AppModule {
    private lateinit var sApplication: App

    fun init(application: App) {
        sApplication = application
    }

    fun provideApplication(): App {
        return sApplication
    }

    fun provideContext(): Context {
        return sApplication.applicationContext
    }

    fun provideResources(): Resources {
        return sApplication.applicationContext.resources
    }
}
