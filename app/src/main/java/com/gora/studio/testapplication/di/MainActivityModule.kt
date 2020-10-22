package com.gora.studio.testapplication.di

import com.gora.studio.testapplication.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {

    @Suppress("unused")
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun mainActivity(): MainActivity
}
