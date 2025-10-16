package com.happybank.app.core.data

import com.happybank.app.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Hilt module for providing networking dependencies
 * Provides Retrofit, OkHttp, and JSON serialization configuration
 *
 * BASE_URL is determined by build flavor:
 * - mock: http://localhost:8080/ (NanoHTTPD server)
 * - prod: https://api.happybank.com/ (real API)
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Provides JSON serializer with custom configuration
     */
    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true // Ignore unknown fields from API
        coerceInputValues = true // Use default values for null fields
        prettyPrint = true // Pretty print for debugging
        isLenient = true // Be lenient with JSON parsing
    }

    /**
     * Provides OkHttp logging interceptor for debugging
     */
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Log full request/response
            // In production, use Level.NONE or Level.BASIC
        }
    }

    /**
     * Provides configured OkHttpClient
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            // Add authentication interceptor here if needed
            // .addInterceptor(authInterceptor)
            .build()
    }

    /**
     * Provides Retrofit instance with Kotlin Serialization
     */
    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        json: Json
    ): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    // Example: Provide specific API service
    // @Provides
    // @Singleton
    // fun provideAccountApi(retrofit: Retrofit): AccountApi {
    //     return retrofit.create(AccountApi::class.java)
    // }
}
