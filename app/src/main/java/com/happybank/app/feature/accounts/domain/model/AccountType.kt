package com.happybank.app.feature.accounts.domain.model

/**
 * Account type enumeration
 * Represents different types of bank accounts
 */
enum class AccountType {
    CHECKING,
    SAVINGS,
    CREDIT_CARD,
    INVESTMENT;

    /**
     * Human-readable display name for the account type
     */
    val displayName: String
        get() = when (this) {
            CHECKING -> "Checking"
            SAVINGS -> "Savings"
            CREDIT_CARD -> "Credit Card"
            INVESTMENT -> "Investment"
        }

    companion object {
        /**
         * Convert string value to AccountType
         * @param value String representation of account type
         * @return Corresponding AccountType, defaults to CHECKING if unknown
         */
        fun fromString(value: String): AccountType = when (value.uppercase()) {
            "CHECKING" -> CHECKING
            "SAVINGS" -> SAVINGS
            "CREDIT_CARD", "CREDIT" -> CREDIT_CARD
            "INVESTMENT" -> INVESTMENT
            else -> CHECKING // Default fallback
        }
    }
}
