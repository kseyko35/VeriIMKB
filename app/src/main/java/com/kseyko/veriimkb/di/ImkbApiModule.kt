package com.kseyko.veriimkb.di

import com.kseyko.veriimkb.data.api.AuthService
import com.kseyko.veriimkb.data.api.ImkbService
import com.kseyko.veriimkb.helper.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImkbApiModule {


    @Singleton
    @Provides
    fun provideInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.HEADERS
    }

    @Singleton
    @Provides
    fun provideHttpClient() = OkHttpClient.Builder().apply {
        //TODO
//        val cacheSize = (10 * 1024 * 1024).toLong() // 10 MB alan]
//        val cache = Cache(appContext.cacheDir, cacheSize)
        addInterceptor(provideInterceptor()).readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
//            .cache(cache)
    }.build()

    @Provides
    @Singleton
    fun retrofitClient(): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideHttpClient())
            .build()

    @Provides
    @Singleton
    fun createApi(): AuthService =
        retrofitClient().create(AuthService::class.java)

    @Singleton
    @Provides
    fun provideHandshakeService(): ImkbService {
        return retrofitClient().create(ImkbService::class.java)
    }
}
