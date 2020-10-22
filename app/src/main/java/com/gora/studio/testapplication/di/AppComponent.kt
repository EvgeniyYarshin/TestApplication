package com.gora.studio.testapplication.di

import android.app.Application
import com.gora.studio.testapplication.TestApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    AndroidInjectionModule::class,
    MainActivityModule::class
])
interface AppComponent: AndroidInjector<TestApp> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    override fun inject(testApp: TestApp)
}