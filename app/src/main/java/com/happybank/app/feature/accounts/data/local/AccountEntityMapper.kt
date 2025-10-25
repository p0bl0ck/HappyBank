package com.happybank.app.feature.accounts.data.local

import com.happybank.app.core.domain.model.Currency
import com.happybank.app.core.domain.model.Money
import com.happybank.app.feature.accounts.domain.model.Account
import com.happybank.app.feature.accounts.domain.model.AccountType
import java.time.Instant

/**
 * Mapper for converting between Account (domain layer) and AccountEntity (database layer)
 *
 * Bidirectional mapping:
 * - Account → AccountEntity (toEntity) for saving to database
 * - AccountEntity → Account (toDomain) for reading from database
 *
 * Responsibilities:
 * - Decompose Money value object into amount + currency code
 * - Convert Instant timestamps to Long (epoch milliseconds)
 * - Convert AccountType enum to String
 * - Reverse all conversions when reading from database
 *
 * Design decisions:
 * - Extension functions for idiomatic Kotlin
 * - Type-safe conversions using value objects
 * - Preserves all data integrity during round-trip conversion
 */

/**
 * Convert Account domain model to AccountEntity for database storage
 *
 * @receiver Account domain model
 * @return AccountEntity for Room database
 */
fun Account.toEntity(): AccountEntity {
    return AccountEntity(
        id = this.id,
        accountNumber = this.accountNumber,
        accountType = this.type.name,
        balanceAmount = this.balance.amount,
        balanceCurrency = this.balance.currency.code,
        owner = this.owner,
        createdAt = this.createdAt.toEpochMilli(),
        lastUpdated = this.lastUpdated.toEpochMilli()
    )
}

/**
 * Convert AccountEntity from database to Account domain model
 *
 * @receiver AccountEntity from Room database
 * @return Account domain model
 */
fun AccountEntity.toDomain(): Account {
    val currency = Currency.fromCode(this.balanceCurrency)
    val balance = Money(this.balanceAmount, currency)

    return Account(
        id = this.id,
        accountNumber = this.accountNumber,
        type = AccountType.fromString(this.accountType),
        balance = balance,
        owner = this.owner,
        createdAt = Instant.ofEpochMilli(this.createdAt),
        lastUpdated = Instant.ofEpochMilli(this.lastUpdated)
    )
}
