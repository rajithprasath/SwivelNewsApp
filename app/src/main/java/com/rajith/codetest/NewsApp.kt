package com.rajith.codetest

import android.app.Application
import android.content.Context
import com.rajith.codetest.di.CoreComponent
import com.rajith.codetest.di.DaggerCoreComponent

import timber.log.Timber

class NewsApp : Application() {



    lateinit var coreComponent: CoreComponent

    companion object {
        @JvmStatic
        fun coreComponent(context: Context)= (context.applicationContext as? NewsApp)?.coreComponent
    }

    override fun onCreate() {
        super.onCreate()

        initCoreDependencyInjection()
        initTimber()
    }



    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }


    private fun initCoreDependencyInjection() {
        coreComponent = DaggerCoreComponent
            .builder()
            .application(this)
            .build()
    }

}
