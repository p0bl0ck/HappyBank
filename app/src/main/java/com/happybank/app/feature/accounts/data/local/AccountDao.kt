package com.happybank.app.feature.accounts.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for account database operations
 *
 * Design decisions:
 * - Flow<AccountEntity?> for reactive database updates
 * - suspend functions for one-time operations
 * - OnConflictStrategy.REPLACE for upsert behavior
 * - Separate insert and query methods for better control
 */
@Dao
interface AccountDao {

    /**
     * Insert or update an account
     * If account with same ID exists, it will be replaced
     *
     * @param account The account to insert/update
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(account: AccountEntity)

    /**
     * Get account by ID as a reactive stream
     * Automatically updates when database changes
     *
     * @param accountId The account ID to fetch
     * @return Flow<AccountEntity?> - null if account not found
     */
    @Query("SELECT * FROM accounts WHERE id = :accountId")
    fun getAccountFlow(accountId: String): Flow<AccountEntity?>

    /**
     * Get account by ID (one-time query)
     *
     * @param accountId The account ID to fetch
     * @return AccountEntity? - null if account not found
     */
    @Query("SELECT * FROM accounts WHERE id = :accountId")
    suspend fun getAccount(accountId: String): AccountEntity?

    /**
     * Get all accounts as a reactive stream
     *
     * @return Flow<List<AccountEntity>> - empty list if no accounts
     */
    @Query("SELECT * FROM accounts ORDER BY last_updated DESC")
    fun getAllAccountsFlow(): Flow<List<AccountEntity>>

    /**
     * Get all accounts (one-time query)
     *
     * @return List<AccountEntity> - empty list if no accounts
     */
    @Query("SELECT * FROM accounts ORDER BY last_updated DESC")
    suspend fun getAllAccounts(): List<AccountEntity>

    /**
     * Delete account by ID
     *
     * @param accountId The account ID to delete
     */
    @Query("DELETE FROM accounts WHERE id = :accountId")
    suspend fun deleteAccount(accountId: String)

    /**
     * Delete all accounts
     * Useful for logout or cache clearing
     */
    @Query("DELETE FROM accounts")
    suspend fun deleteAllAccounts()

    /**
     * Check if account exists
     *
     * @param accountId The account ID to check
     * @return true if account exists, false otherwise
     */
    @Query("SELECT EXISTS(SELECT 1 FROM accounts WHERE id = :accountId)")
    suspend fun accountExists(accountId: String): Boolean
}
