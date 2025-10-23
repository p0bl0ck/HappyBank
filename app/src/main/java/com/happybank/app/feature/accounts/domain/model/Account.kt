package com.happybank.app.feature.accounts.domain.model

import com.happybank.app.core.domain.model.Money
import java.time.Instant

/**
 * Domain model representing a bank account
 * Pure domain model with no external dependencies (no Room, no Serialization)
 *
 * This model uses the Money value object for type-safe monetary operations
 */
data class Account(
    val id: String,
    val accountNumber: String,
    val type: AccountType,
    val balance: Money,
    val owner: String,
    val createdAt: Instant,
    val lastUpdated: Instant = Instant.now()
) {
    /**
     * Masked account number for display purposes
     * Shows only last 4 digits for security
     * Example: "****7890"
     */
    val maskedAccountNumber: String
        get() = if (accountNumber.length >= 4) {
            "****${accountNumber.takeLast(4)}"
        } else {
            accountNumber
        }

    /**
     * Check if account has sufficient balance for a transaction
     * @param amount The amount to check
     * @return true if balance is sufficient
     * @throws IllegalArgumentException if currencies don't match
     */
    fun hasSufficientBalance(amount: Money): Boolean {
        require(amount.currency == balance.currency) {
            "Currency mismatch: ${amount.currency.code} vs ${balance.currency.code}"
        }
        return balance >= amount
    }

    /**
     * Check if account data is fresh (recently updated)
     * Data is considered fresh if updated within last 5 minutes
     */
    val isFresh: Boolean
        get() = Instant.now().minusSeconds(FRESH_THRESHOLD_SECONDS).isBefore(lastUpdated)

    companion object {
        private const val FRESH_THRESHOLD_SECONDS = 300L // 5 minutes
    }
}
