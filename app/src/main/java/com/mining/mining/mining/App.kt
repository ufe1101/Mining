package com.mining.mining.mining

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.facebook.stetho.Stetho
import com.mining.mining.base.util.AppModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppModule.init(this)
        Stetho.initializeWithDefaults(this)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        MultiDex.install(this)
    }

}