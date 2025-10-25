package com.happybank.app.feature.accounts.data.api

import com.happybank.app.core.domain.model.Currency
import com.happybank.app.core.domain.model.usd
import com.happybank.app.feature.accounts.domain.model.AccountType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("AccountDto Mapper Tests")
class AccountDtoMapperTest {

    @Test
    @DisplayName("Convert DTO to domain model with all fields")
    fun `convert DTO to domain model with all fields`() {
        val dto = AccountDto(
            accountId = "ACC-123",
            accountNumber = "1234567890",
            accountType = "CHECKING",
            balance = 1500.50,
            currency = "USD",
            userName = "Jane Doe",
            createdAt = "2024-01-15T10:30:00Z"
        )

        val domain = dto.toDomain()

        assertEquals("ACC-123", domain.id)
        assertEquals("1234567890", domain.accountNumber)
        assertEquals(AccountType.CHECKING, domain.type)
        assertEquals(1500.50.usd(), domain.balance)
        assertEquals("Jane Doe", domain.owner)
        assertNotNull(domain.createdAt)
        assertNotNull(domain.lastUpdated)
    }

    @Test
    @DisplayName("Convert DTO with null createdAt")
    fun `convert DTO with null createdAt`() {
        val dto = AccountDto(
            accountId = "ACC-456",
            accountNumber = "9876543210",
            accountType = "SAVINGS",
            balance = 5000.0,
            currency = "USD",
            userName = "John Smith",
            createdAt = null
        )

        val domain = dto.toDomain()

        assertEquals("ACC-456", domain.id)
        assertNotNull(domain.createdAt) // Should fallback to Instant.now()
    }

    @Test
    @DisplayName("Convert different currencies")
    fun `convert different currencies`() {
        val usdDto = AccountDto("1", "123", "CHECKING", 100.0, "USD", "User")
        val eurDto = AccountDto("2", "456", "CHECKING", 200.0, "EUR", "User")
        val gbpDto = AccountDto("3", "789", "CHECKING", 300.0, "GBP", "User")

        assertEquals(Currency.USD, usdDto.toDomain().balance.currency)
        assertEquals(Currency.EUR, eurDto.toDomain().balance.currency)
        assertEquals(Currency.GBP, gbpDto.toDomain().balance.currency)
    }

    @Test
    @DisplayName("Convert different account types")
    fun `convert different account types`() {
        val checking = AccountDto("1", "123", "CHECKING", 100.0, "USD", "User")
        val savings = AccountDto("2", "456", "SAVINGS", 200.0, "USD", "User")
        val credit = AccountDto("3", "789", "CREDIT_CARD", 300.0, "USD", "User")
        val investment = AccountDto("4", "012", "INVESTMENT", 400.0, "USD", "User")

        assertEquals(AccountType.CHECKING, checking.toDomain().type)
        assertEquals(AccountType.SAVINGS, savings.toDomain().type)
        assertEquals(AccountType.CREDIT_CARD, credit.toDomain().type)
        assertEquals(AccountType.INVESTMENT, investment.toDomain().type)
    }

    @Test
    @DisplayName("Map accountId to id and userName to owner")
    fun `map accountId to id and userName to owner`() {
        val dto = AccountDto(
            accountId = "ACC-999",
            accountNumber = "1234567890",
            accountType = "CHECKING",
            balance = 1000.0,
            currency = "USD",
            userName = "Alice Johnson"
        )

        val domain = dto.toDomain()

        assertEquals("ACC-999", domain.id)
        assertEquals("Alice Johnson", domain.owner)
    }
}
