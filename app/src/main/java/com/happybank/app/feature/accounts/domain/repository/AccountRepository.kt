package com.happybank.app.feature.accounts.domain.repository

import com.happybank.app.feature.accounts.domain.model.Account
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for account operations
 *
 * This interface follows the Repository pattern from Clean Architecture:
 * - Defined in the domain layer (pure business logic)
 * - Implemented in the data layer (data sources)
 * - Used by use cases to access account data
 *
 * Design decisions:
 * - Flow<Result<Account>> for reactive updates from cache/network
 * - suspend fun for one-time operations like refresh
 * - Kotlin's built-in Result<T> for error handling
 * - NO Loading state here (that's a UI concern in ViewModel)
 */
interface AccountRepository {

    /**
     * Get account as a reactive stream
     * Emits from cache first (if available), then network updates
     *
     * Flow allows:
     * - Automatic updates when cache changes
     * - Database observers to propagate changes
     * - Efficient resource management
     *
     * @return Flow of Result<Account>
     *         - Success: Account data available
     *         - Failure: Network error, database error, etc.
     */
    fun getAccount(): Flow<Result<Account>>

    /**
     * Force refresh account from network
     * Use this for pull-to-refresh or manual refresh actions
     *
     * @return Result<Account>
     *         - Success: Fresh account data from network
     *         - Failure: Network error, parsing error, etc.
     */
    suspend fun refreshAccount(): Result<Account>

    /**
     * Clear cached account data
     * Useful for logout or cache invalidation
     */
    suspend fun clearCache()
}
