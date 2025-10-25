package com.happybank.app.core.data.local

import android.content.Context
import androidx.room.Room
import com.happybank.app.feature.accounts.data.local.AccountDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module providing database dependencies
 *
 * Provides:
 * - AppDatabase instance (singleton)
 * - All DAOs (singleton)
 *
 * Design decisions:
 * - @Singleton scope for database (single instance per app lifetime)
 * - Room.databaseBuilder for production database
 * - fallbackToDestructiveMigration() for dev convenience (remove in production)
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * Provides the singleton AppDatabase instance
     *
     * @param context Application context
     * @return AppDatabase instance
     */
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration() // TODO: Remove in production, add proper migrations
            .build()
    }

    /**
     * Provides AccountDao
     *
     * @param database The AppDatabase instance
     * @return AccountDao instance
     */
    @Provides
    @Singleton
    fun provideAccountDao(database: AppDatabase): AccountDao {
        return database.accountDao()
    }
}
