package com.example.simplifiedinstagramstorymodule.core

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.example.simplifiedinstagramstorymodule.core.di.ApplicationComponent
import com.example.simplifiedinstagramstorymodule.core.di.ApplicationModule
import com.example.simplifiedinstagramstorymodule.core.di.DaggerApplicationComponent

class StoryModuleApplication : Application() {

    var appComponent: ApplicationComponent? = DaggerApplicationComponent
        .builder()
        .applicationModule(ApplicationModule(this))
        .build()

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        this.injectMembers()
    }

    private fun injectMembers() = appComponent?.inject(this)
}