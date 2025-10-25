package com.happybank.app.feature.accounts.domain.model

import com.happybank.app.core.domain.model.Currency
import com.happybank.app.core.domain.model.Money
import com.happybank.app.core.domain.model.usd
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.Instant

@DisplayName("Account Domain Model Tests")
class AccountTest {

    @Nested
    @DisplayName("Account Creation")
    inner class AccountCreation {

        @Test
        @DisplayName("Create account with all required fields")
        fun `create account with all required fields`() {
            val account = createTestAccount()

            assertEquals("ACC-001", account.id)
            assertEquals("1234567890", account.accountNumber)
            assertEquals(AccountType.CHECKING, account.type)
            assertEquals(1000.0.usd(), account.balance)
            assertEquals("John Doe", account.owner)
        }

        @Test
        @DisplayName("Create account with different account types")
        fun `create account with different account types`() {
            val checking = createTestAccount { type = AccountType.CHECKING }
            val savings = createTestAccount { type = AccountType.SAVINGS }
            val credit = createTestAccount { type = AccountType.CREDIT_CARD }
            val investment = createTestAccount { type = AccountType.INVESTMENT }

            assertEquals(AccountType.CHECKING, checking.type)
            assertEquals(AccountType.SAVINGS, savings.type)
            assertEquals(AccountType.CREDIT_CARD, credit.type)
            assertEquals(AccountType.INVESTMENT, investment.type)
        }

        @Test
        @DisplayName("Create account with different currencies")
        fun `create account with different currencies`() {
            val usdAccount = createTestAccount { balance = 1000.0.usd() }
            val eurAccount = createTestAccount { balance = Money.eur(1000.0) }
            val gbpAccount = createTestAccount { balance = Money.gbp(1000.0) }

            assertEquals(Currency.USD, usdAccount.balance.currency)
            assertEquals(Currency.EUR, eurAccount.balance.currency)
            assertEquals(Currency.GBP, gbpAccount.balance.currency)
        }
    }

    @Nested
    @DisplayName("Masked Account Number")
    inner class MaskedAccountNumber {

        @Test
        @DisplayName("Mask account number with 10 digits")
        fun `mask account number with 10 digits`() {
            val account = createTestAccount { accountNumber = "1234567890" }
            assertEquals("****7890", account.maskedAccountNumber)
        }

        @Test
        @DisplayName("Mask account number with 8 digits")
        fun `mask account number with 8 digits`() {
            val account = createTestAccount { accountNumber = "12345678" }
            assertEquals("****5678", account.maskedAccountNumber)
        }

        @Test
        @DisplayName("Mask account number with exactly 4 digits")
        fun `mask account number with exactly 4 digits`() {
            val account = createTestAccount { accountNumber = "1234" }
            assertEquals("****1234", account.maskedAccountNumber)
        }

        @Test
        @DisplayName("Show full account number if less than 4 digits")
        fun `show full account number if less than 4 digits`() {
            val account = createTestAccount { accountNumber = "123" }
            assertEquals("123", account.maskedAccountNumber)
        }

        @Test
        @DisplayName("Handle empty account number")
        fun `handle empty account number`() {
            val account = createTestAccount { accountNumber = "" }
            assertEquals("", account.maskedAccountNumber)
        }
    }

    @Nested
    @DisplayName("Sufficient Balance Validation")
    inner class SufficientBalanceValidation {

        @Test
        @DisplayName("Has sufficient balance when balance is greater")
        fun `has sufficient balance when balance is greater`() {
            val account = createTestAccount { balance = 1000.0.usd() }

            assertTrue(account.hasSufficientBalance(500.0.usd()))
            assertTrue(account.hasSufficientBalance(999.0.usd()))
        }

        @Test
        @DisplayName("Has sufficient balance when balance equals amount")
        fun `has sufficient balance when balance equals amount`() {
            val account = createTestAccount { balance = 1000.0.usd() }

            assertTrue(account.hasSufficientBalance(1000.0.usd()))
        }

        @Test
        @DisplayName("Does not have sufficient balance when balance is less")
        fun `does not have sufficient balance when balance is less`() {
            val account = createTestAccount { balance = 1000.0.usd() }

            assertFalse(account.hasSufficientBalance(1001.0.usd()))
            assertFalse(account.hasSufficientBalance(2000.0.usd()))
        }

        @Test
        @DisplayName("Has sufficient balance with zero amount")
        fun `has sufficient balance with zero amount`() {
            val account = createTestAccount { balance = 1000.0.usd() }

            assertTrue(account.hasSufficientBalance(Money.zero(Currency.USD)))
        }

        @Test
        @DisplayName("Throws exception when currencies do not match")
        fun `throws exception when currencies do not match`() {
            val usdAccount = createTestAccount { balance = 1000.0.usd() }
            val eurAmount = Money.eur(500.0)

            val exception = assertThrows<IllegalArgumentException> {
                usdAccount.hasSufficientBalance(eurAmount)
            }

            assertTrue(exception.message!!.contains("Currency mismatch"))
            assertTrue(exception.message!!.contains("USD"))
            assertTrue(exception.message!!.contains("EUR"))
        }

        @Test
        @DisplayName("Works with different currencies when matching")
        fun `works with different currencies when matching`() {
            val eurAccount = createTestAccount { balance = Money.eur(1000.0) }

            assertTrue(eurAccount.hasSufficientBalance(Money.eur(500.0)))
            assertFalse(eurAccount.hasSufficientBalance(Money.eur(1500.0)))
        }
    }

    @Nested
    @DisplayName("Fresh Data Check")
    inner class FreshDataCheck {

        @Test
        @DisplayName("Account is fresh when just created")
        fun `account is fresh when just created`() {
            val account = createTestAccount { lastUpdated = Instant.now() }

            assertTrue(account.isFresh)
        }

        @Test
        @DisplayName("Account is fresh when updated 4 minutes ago")
        fun `account is fresh when updated 4 minutes ago`() {
            val fourMinutesAgo = Instant.now().minusSeconds(240)
            val account = createTestAccount { lastUpdated = fourMinutesAgo }

            assertTrue(account.isFresh)
        }

        @Test
        @DisplayName("Account is not fresh at exactly 5 minutes")
        fun `account is not fresh at exactly 5 minutes`() {
            val fiveMinutesAgo = Instant.now().minusSeconds(300)
            val account = createTestAccount { lastUpdated = fiveMinutesAgo }

            // At exactly 5 minutes boundary, considered stale
            assertFalse(account.isFresh)
        }

        @Test
        @DisplayName("Account is not fresh when updated 6 minutes ago")
        fun `account is not fresh when updated 6 minutes ago`() {
            val sixMinutesAgo = Instant.now().minusSeconds(360)
            val account = createTestAccount { lastUpdated = sixMinutesAgo }

            assertFalse(account.isFresh)
        }

        @Test
        @DisplayName("Account is not fresh when updated 1 hour ago")
        fun `account is not fresh when updated 1 hour ago`() {
            val oneHourAgo = Instant.now().minusSeconds(3600)
            val account = createTestAccount { lastUpdated = oneHourAgo }

            assertFalse(account.isFresh)
        }
    }

    @Nested
    @DisplayName("AccountType Enum")
    inner class AccountTypeEnum {

        @Test
        @DisplayName("Display names are correct")
        fun `display names are correct`() {
            assertEquals("Checking", AccountType.CHECKING.displayName)
            assertEquals("Savings", AccountType.SAVINGS.displayName)
            assertEquals("Credit Card", AccountType.CREDIT_CARD.displayName)
            assertEquals("Investment", AccountType.INVESTMENT.displayName)
        }

        @Test
        @DisplayName("Convert from string - uppercase")
        fun `convert from string - uppercase`() {
            assertEquals(AccountType.CHECKING, AccountType.fromString("CHECKING"))
            assertEquals(AccountType.SAVINGS, AccountType.fromString("SAVINGS"))
            assertEquals(AccountType.CREDIT_CARD, AccountType.fromString("CREDIT_CARD"))
            assertEquals(AccountType.INVESTMENT, AccountType.fromString("INVESTMENT"))
        }

        @Test
        @DisplayName("Convert from string - lowercase")
        fun `convert from string - lowercase`() {
            assertEquals(AccountType.CHECKING, AccountType.fromString("checking"))
            assertEquals(AccountType.SAVINGS, AccountType.fromString("savings"))
            assertEquals(AccountType.CREDIT_CARD, AccountType.fromString("credit_card"))
            assertEquals(AccountType.INVESTMENT, AccountType.fromString("investment"))
        }

        @Test
        @DisplayName("Convert from string - mixed case")
        fun `convert from string - mixed case`() {
            assertEquals(AccountType.CHECKING, AccountType.fromString("ChEcKiNg"))
            assertEquals(AccountType.SAVINGS, AccountType.fromString("SaViNgS"))
        }

        @Test
        @DisplayName("Convert CREDIT to CREDIT_CARD")
        fun `convert CREDIT to CREDIT_CARD`() {
            assertEquals(AccountType.CREDIT_CARD, AccountType.fromString("CREDIT"))
            assertEquals(AccountType.CREDIT_CARD, AccountType.fromString("credit"))
        }

        @Test
        @DisplayName("Unknown values default to CHECKING")
        fun `unknown values default to CHECKING`() {
            assertEquals(AccountType.CHECKING, AccountType.fromString("UNKNOWN"))
            assertEquals(AccountType.CHECKING, AccountType.fromString("invalid"))
            assertEquals(AccountType.CHECKING, AccountType.fromString(""))
        }
    }

    @Nested
    @DisplayName("Real-World Scenarios")
    inner class RealWorldScenarios {

        @Test
        @DisplayName("Check if can make withdrawal")
        fun `check if can make withdrawal`() {
            val account = createTestAccount { balance = 1000.0.usd() }
            val withdrawalAmount = 300.0.usd()

            assertTrue(account.hasSufficientBalance(withdrawalAmount))

            // Simulate remaining balance
            val remainingBalance = account.balance - withdrawalAmount
            assertEquals(700.0.usd(), remainingBalance)
        }

        @Test
        @DisplayName("Check if can make large purchase")
        fun `check if can make large purchase`() {
            val account = createTestAccount { balance = 500.0.usd() }
            val purchaseAmount = 1000.0.usd()

            assertFalse(account.hasSufficientBalance(purchaseAmount))
        }

        @Test
        @DisplayName("Multiple accounts with different balances")
        fun `multiple accounts with different balances`() {
            val checkingAccount = createTestAccount {
                id = "CHK-001"
                type = AccountType.CHECKING
                balance = 5000.0.usd()
            }

            val savingsAccount = createTestAccount {
                id = "SAV-001"
                type = AccountType.SAVINGS
                balance = 10000.0.usd()
            }

            assertTrue(checkingAccount.hasSufficientBalance(4000.0.usd()))
            assertTrue(savingsAccount.hasSufficientBalance(9000.0.usd()))
            assertTrue(savingsAccount.balance > checkingAccount.balance)
        }

        @Test
        @DisplayName("Account security - masked number for display")
        fun `account security - masked number for display`() {
            val account = createTestAccount { accountNumber = "9876543210" }

            // Never show full account number in UI
            assertEquals("****3210", account.maskedAccountNumber)

            // Full number only for backend operations
            assertEquals("9876543210", account.accountNumber)
        }

        @Test
        @DisplayName("Cache invalidation based on freshness")
        fun `cache invalidation based on freshness`() {
            val freshAccount = createTestAccount { lastUpdated = Instant.now() }
            val staleAccount = createTestAccount {
                lastUpdated = Instant.now().minusSeconds(600) // 10 minutes ago
            }

            assertTrue(freshAccount.isFresh) // No need to refresh
            assertFalse(staleAccount.isFresh) // Should refresh from API
        }
    }

    // Test Account Builder Pattern
    private class AccountBuilder {
        var id: String = "ACC-001"
        var accountNumber: String = "1234567890"
        var type: AccountType = AccountType.CHECKING
        var balance: Money = 1000.0.usd()
        var owner: String = "John Doe"
        var createdAt: Instant = Instant.now().minusSeconds(86400) // 1 day ago
        var lastUpdated: Instant = Instant.now()

        fun build(): Account = Account(
            id = id,
            accountNumber = accountNumber,
            type = type,
            balance = balance,
            owner = owner,
            createdAt = createdAt,
            lastUpdated = lastUpdated
        )
    }

    private fun createTestAccount(init: AccountBuilder.() -> Unit = {}): Account {
        return AccountBuilder().apply(init).build()
    }
}
