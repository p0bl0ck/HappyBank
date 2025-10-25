package com.happybank.app.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.happybank.app.feature.accounts.data.local.AccountDao
import com.happybank.app.feature.accounts.data.local.AccountEntity

/**
 * HappyBank application database
 *
 * This is the main Room database for the app
 * - Centralized database definition
 * - Version management for schema migrations
 * - Provides DAOs for all features
 *
 * Design decisions:
 * - Single database for all features (simpler than multiple databases)
 * - Version 1 as initial release
 * - exportSchema = false (no schema export for now, can enable later)
 */
@Database(
    entities = [
        AccountEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Provides access to account database operations
     */
    abstract fun accountDao(): AccountDao

    companion object {
        const val DATABASE_NAME = "happybank.db"
    }
}
