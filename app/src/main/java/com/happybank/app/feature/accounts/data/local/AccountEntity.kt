package com.happybank.app.feature.accounts.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity representing an account in the local database
 * This is the data layer's database representation
 *
 * Mapping strategy:
 * - AccountEntity (database) ↔ Account (domain) via mapper
 * - Stores monetary values as primitives (amount + currency code)
 * - Stores timestamps as Long (epoch milliseconds) for Room compatibility
 */
@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "account_number")
    val accountNumber: String,

    @ColumnInfo(name = "account_type")
    val accountType: String,

    @ColumnInfo(name = "balance_amount")
    val balanceAmount: Double,

    @ColumnInfo(name = "balance_currency")
    val balanceCurrency: String,

    @ColumnInfo(name = "owner")
    val owner: String,

    @ColumnInfo(name = "created_at")
    val createdAt: Long,

    @ColumnInfo(name = "last_updated")
    val lastUpdated: Long
)
