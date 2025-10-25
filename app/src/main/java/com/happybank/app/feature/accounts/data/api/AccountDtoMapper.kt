package com.happybank.app.feature.accounts.data.api

import com.happybank.app.core.domain.model.Currency
import com.happybank.app.core.domain.model.Money
import com.happybank.app.feature.accounts.domain.model.Account
import com.happybank.app.feature.accounts.domain.model.AccountType
import java.time.Instant
import java.time.format.DateTimeFormatter

/**
 * Mapper for converting AccountDto (API layer) to Account (domain layer)
 *
 * Responsibilities:
 * - Convert API data types to domain value objects
 * - Handle ISO 8601 timestamp parsing
 * - Convert currency string to Currency value object
 * - Convert account type string to AccountType enum
 * - Provide default values for missing data
 *
 * Design decisions:
 * - Extension function for idiomatic Kotlin
 * - Handles null createdAt with fallback to current time
 * - Uses Currency.fromCode() for type-safe currency conversion
 * - Uses AccountType.fromString() for type-safe enum conversion
 */

/**
 * Convert AccountDto to Account domain model
 *
 * @receiver AccountDto from API response
 * @return Account domain model
 */
fun AccountDto.toDomain(): Account {
    val currency = Currency.fromCode(this.currency)
    val balanceMoney = Money(this.balance, currency)

    val createdAtInstant = this.createdAt?.let {
        parseIso8601Timestamp(it)
    } ?: Instant.now()

    return Account(
        id = this.accountId,
        accountNumber = this.accountNumber,
        type = AccountType.fromString(this.accountType),
        balance = balanceMoney,
        owner = this.userName,
        createdAt = createdAtInstant,
        lastUpdated = Instant.now() // Set to now since API just returned fresh data
    )
}

/**
 * Parse ISO 8601 timestamp string to Instant
 *
 * Supported formats:
 * - 2024-01-15T10:30:00Z (ISO_INSTANT)
 * - 2024-01-15T10:30:00.123Z (ISO_INSTANT with milliseconds)
 * - 2024-01-15T10:30:00+00:00 (ISO_DATE_TIME)
 *
 * @param timestamp ISO 8601 timestamp string
 * @return Instant representing the timestamp, or current time if parsing fails
 */
private fun parseIso8601Timestamp(timestamp: String): Instant {
    return try {
        Instant.from(DateTimeFormatter.ISO_INSTANT.parse(timestamp))
    } catch (e: java.time.format.DateTimeParseException) {
        // Fallback: try parsing as ISO_DATE_TIME
        try {
            Instant.from(DateTimeFormatter.ISO_DATE_TIME.parse(timestamp))
        } catch (e: java.time.format.DateTimeParseException) {
            // Last fallback: use current time if both formats fail
            // This is acceptable as the API should return valid timestamps
            Instant.now()
        }
    }
}
