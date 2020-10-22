package com.gora.studio.testapplication.di

import android.app.Application
import androidx.room.Room
import com.gora.studio.testapplication.BuildConfig
import com.gora.studio.testapplication.api.TestApi
import com.gora.studio.testapplication.db.JsonDao
import com.gora.studio.testapplication.db.TestDb
import com.gora.studio.testapplication.util.LiveDataCallAdapter
import com.gora.studio.testapplication.util.LiveDataCallAdapterFactory
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {
    @Provides
    @Singleton
    fun loggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun testClient(): OkHttpClient = OkHttpClient().newBuilder()
        .addInterceptor(loggingInterceptor())
        .build()

    @Singleton
    @Provides
    fun testApi(): TestApi {
        return Retrofit.Builder()
            .client(testClient())
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(TestApi::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): TestDb {
        return Room
            .databaseBuilder(app, TestDb::class.java, "test.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideContactsResponseDao(db: TestDb): JsonDao {
        return db.jsonDao()
    }
}