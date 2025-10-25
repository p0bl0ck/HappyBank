package com.happybank.app.feature.accounts.data.local

import com.happybank.app.core.domain.model.Currency
import com.happybank.app.core.domain.model.Money
import com.happybank.app.core.domain.model.usd
import com.happybank.app.feature.accounts.domain.model.Account
import com.happybank.app.feature.accounts.domain.model.AccountType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.Instant

@DisplayName("AccountEntity Mapper Tests")
class AccountEntityMapperTest {

    @Test
    @DisplayName("Convert domain to entity")
    fun `convert domain to entity`() {
        val domain = Account(
            id = "ACC-001",
            accountNumber = "1234567890",
            type = AccountType.CHECKING,
            balance = 1000.0.usd(),
            owner = "John Doe",
            createdAt = Instant.parse("2024-10-24T12:00:00Z"),
            lastUpdated = Instant.parse("2024-10-25T12:00:00Z")
        )

        val entity = domain.toEntity()

        assertEquals("ACC-001", entity.id)
        assertEquals("1234567890", entity.accountNumber)
        assertEquals("CHECKING", entity.accountType)
        assertEquals(1000.0, entity.balanceAmount)
        assertEquals("USD", entity.balanceCurrency)
        assertEquals("John Doe", entity.owner)
    }

    @Test
    @DisplayName("Convert entity to domain")
    fun `convert entity to domain`() {
        val entity = AccountEntity(
            id = "ACC-001",
            accountNumber = "1234567890",
            accountType = "CHECKING",
            balanceAmount = 1000.0,
            balanceCurrency = "USD",
            owner = "John Doe",
            createdAt = Instant.parse("2024-10-24T12:00:00Z").toEpochMilli(),
            lastUpdated = Instant.parse("2024-10-25T12:00:00Z").toEpochMilli()
        )

        val domain = entity.toDomain()

        assertEquals("ACC-001", domain.id)
        assertEquals("1234567890", domain.accountNumber)
        assertEquals(AccountType.CHECKING, domain.type)
        assertEquals(1000.0.usd(), domain.balance)
        assertEquals("John Doe", domain.owner)
    }

    @Test
    @DisplayName("Round-trip conversion preserves data")
    fun `round-trip conversion preserves data`() {
        val original = Account(
            id = "ACC-999",
            accountNumber = "9876543210",
            type = AccountType.SAVINGS,
            balance = Money.eur(12345.67),
            owner = "Jane Smith",
            createdAt = Instant.parse("2024-01-01T00:00:00Z"),
            lastUpdated = Instant.parse("2024-10-25T12:30:00Z")
        )

        val entity = original.toEntity()
        val roundTrip = entity.toDomain()

        assertEquals(original, roundTrip)
    }

    @Test
    @DisplayName("Handle all currencies")
    fun `handle all currencies`() {
        val currencies = listOf(Currency.USD, Currency.EUR, Currency.GBP)

        currencies.forEach { currency ->
            val domain = Account(
                id = "ACC-001",
                accountNumber = "1234567890",
                type = AccountType.CHECKING,
                balance = Money(100.0, currency),
                owner = "Test",
                createdAt = Instant.parse("2024-10-24T12:00:00Z"),
                lastUpdated = Instant.parse("2024-10-25T12:00:00Z")
            )

            val roundTrip = domain.toEntity().toDomain()
            assertEquals(currency, roundTrip.balance.currency)
            assertEquals(100.0, roundTrip.balance.amount)
        }
    }

    @Test
    @DisplayName("Handle all account types")
    fun `handle all account types`() {
        val types = listOf(
            AccountType.CHECKING,
            AccountType.SAVINGS,
            AccountType.CREDIT_CARD,
            AccountType.INVESTMENT
        )

        types.forEach { type ->
            val domain = Account(
                id = "ACC-001",
                accountNumber = "1234567890",
                type = type,
                balance = 100.0.usd(),
                owner = "Test",
                createdAt = Instant.parse("2024-10-24T12:00:00Z"),
                lastUpdated = Instant.parse("2024-10-25T12:00:00Z")
            )

            val roundTrip = domain.toEntity().toDomain()
            assertEquals(type, roundTrip.type)
        }
    }
}
