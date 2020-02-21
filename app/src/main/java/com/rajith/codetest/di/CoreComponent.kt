package com.rajith.codetest.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.rajith.codetest.database.NewsDatabase
import dagger.BindsInstance
import dagger.Component

import com.rajith.codetest.di.modules.*
import retrofit2.Retrofit
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        ContextModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        SharedPreferenceModule::class
    ]
)
interface CoreComponent {
    fun context() : Context
    fun sharedPreference() : SharedPreferences
    fun sharedPreferenceEditor() : SharedPreferences.Editor
    fun newsDatabase() : NewsDatabase
    fun retrofit() : Retrofit


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): CoreComponent
    }
}