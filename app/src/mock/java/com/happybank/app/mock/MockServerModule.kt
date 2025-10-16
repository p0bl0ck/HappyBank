package com.happybank.app.mock

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for mock server
 * Only included in mock build flavor
 */
@Module
@InstallIn(SingletonComponent::class)
object MockServerModule {

    @Provides
    @Singleton
    fun provideMockServer(
        @ApplicationContext context: Context
    ): MockServer {
        return MockServer(context)
    }
}
