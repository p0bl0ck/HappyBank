package com.happybank.app.core.domain.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("Money Value Object Tests")
class MoneyTest {

    @Nested
    @DisplayName("Money Creation")
    inner class MoneyCreation {

        @Test
        @DisplayName("Create money with amount and currency")
        fun `create money with amount and currency`() {
            val money = Money(100.0, Currency.USD)

            assertEquals(100.0, money.amount)
            assertEquals(Currency.USD, money.currency)
        }

        @Test
        @DisplayName("Create zero money")
        fun `create zero money`() {
            val zero = Money.zero()

            assertEquals(0.0, zero.amount)
            assertEquals(Currency.USD, zero.currency)
            assertTrue(zero.isZero)
        }

        @Test
        @DisplayName("Create zero money with specific currency")
        fun `create zero money with specific currency`() {
            val zeroEur = Money.zero(Currency.EUR)

            assertEquals(0.0, zeroEur.amount)
            assertEquals(Currency.EUR, zeroEur.currency)
        }

        @Test
        @DisplayName("Create money using of factory method")
        fun `create money using of factory method`() {
            val money = Money.of(250.50, "EUR")

            assertEquals(250.50, money.amount)
            assertEquals(Currency.EUR, money.currency)
        }

        @Test
        @DisplayName("Create money using currency-specific factory methods")
        fun `create money using currency-specific factory methods`() {
            assertEquals(Currency.USD, Money.usd(100.0).currency)
            assertEquals(Currency.EUR, Money.eur(100.0).currency)
            assertEquals(Currency.GBP, Money.gbp(100.0).currency)
            assertEquals(Currency.JPY, Money.jpy(100.0).currency)
            assertEquals(Currency.PLN, Money.pln(100.0).currency)
        }

        @Test
        @DisplayName("Create money using extension functions")
        fun `create money using extension functions`() {
            val money1 = 100.0.usd()
            val money2 = 50.eur()
            val money3 = 75.5.gbp()

            assertEquals(Money(100.0, Currency.USD), money1)
            assertEquals(Money(50.0, Currency.EUR), money2)
            assertEquals(Money(75.5, Currency.GBP), money3)
        }
    }

    @Nested
    @DisplayName("Money Formatting")
    inner class MoneyFormatting {

        @Test
        @DisplayName("Format USD with symbol")
        fun `format usd with symbol`() {
            val money = Money(1234.56, Currency.USD)
            assertEquals("$1,234.56", money.formatted)
        }

        @Test
        @DisplayName("Format EUR with symbol")
        fun `format eur with symbol`() {
            val money = Money(1234.56, Currency.EUR)
            assertEquals("€1,234.56", money.formatted)
        }

        @Test
        @DisplayName("Format GBP with symbol")
        fun `format gbp with symbol`() {
            val money = Money(1234.56, Currency.GBP)
            assertEquals("£1,234.56", money.formatted)
        }

        @Test
        @DisplayName("Format JPY without decimal places")
        fun `format jpy without decimal places`() {
            val money = Money(1234.0, Currency.JPY)
            assertEquals("¥1,234", money.formatted)
        }

        @Test
        @DisplayName("Format amount without currency symbol")
        fun `format amount without currency symbol`() {
            val money = Money(1234.56, Currency.USD)
            assertEquals("1,234.56", money.amountFormatted)
        }

        @Test
        @DisplayName("Display text includes symbol and formatted amount")
        fun `display text includes symbol and formatted amount`() {
            val money = Money(1234.56, Currency.USD)
            assertEquals("$1,234.56", money.displayText)
        }

        @Test
        @DisplayName("Format large numbers with thousands separators")
        fun `format large numbers with thousands separators`() {
            val money = Money(1234567.89, Currency.USD)
            assertEquals("$1,234,567.89", money.formatted)
        }

        @Test
        @DisplayName("Format negative amounts")
        fun `format negative amounts`() {
            val money = Money(-1234.56, Currency.USD)
            assertEquals("$-1,234.56", money.formatted)
        }

        @Test
        @DisplayName("Format zero amount")
        fun `format zero amount`() {
            val money = Money.zero(Currency.USD)
            assertEquals("$0.00", money.formatted)
        }

        @Test
        @DisplayName("toString returns formatted representation")
        fun `toString returns formatted representation`() {
            val money = Money(100.50, Currency.USD)
            assertEquals("$100.50", money.toString())
        }
    }

    @Nested
    @DisplayName("Money Properties")
    inner class MoneyProperties {

        @Test
        @DisplayName("isPositive returns true for positive amounts")
        fun `isPositive returns true for positive amounts`() {
            val money = Money(100.0, Currency.USD)
            assertTrue(money.isPositive)
            assertFalse(money.isNegative)
            assertFalse(money.isZero)
        }

        @Test
        @DisplayName("isNegative returns true for negative amounts")
        fun `isNegative returns true for negative amounts`() {
            val money = Money(-100.0, Currency.USD)
            assertTrue(money.isNegative)
            assertFalse(money.isPositive)
            assertFalse(money.isZero)
        }

        @Test
        @DisplayName("isZero returns true for zero amounts")
        fun `isZero returns true for zero amounts`() {
            val money = Money(0.0, Currency.USD)
            assertTrue(money.isZero)
            assertFalse(money.isPositive)
            assertFalse(money.isNegative)
        }

        @Test
        @DisplayName("absolute returns positive value")
        fun `absolute returns positive value`() {
            val negative = Money(-100.0, Currency.USD)
            val positive = Money(100.0, Currency.USD)

            assertEquals(Money(100.0, Currency.USD), negative.absolute)
            assertEquals(Money(100.0, Currency.USD), positive.absolute)
        }
    }

    @Nested
    @DisplayName("Money Arithmetic Operations")
    inner class MoneyArithmetic {

        @Test
        @DisplayName("Addition of same currency")
        fun `addition of same currency`() {
            val money1 = Money(100.0, Currency.USD)
            val money2 = Money(50.0, Currency.USD)

            val result = money1 + money2

            assertEquals(Money(150.0, Currency.USD), result)
        }

        @Test
        @DisplayName("Addition throws exception for different currencies")
        fun `addition throws exception for different currencies`() {
            val usd = Money(100.0, Currency.USD)
            val eur = Money(50.0, Currency.EUR)

            val exception = assertThrows<IllegalArgumentException> {
                usd + eur
            }

            assertTrue(exception.message!!.contains("Cannot add different currencies"))
        }

        @Test
        @DisplayName("Subtraction of same currency")
        fun `subtraction of same currency`() {
            val money1 = Money(100.0, Currency.USD)
            val money2 = Money(30.0, Currency.USD)

            val result = money1 - money2

            assertEquals(Money(70.0, Currency.USD), result)
        }

        @Test
        @DisplayName("Subtraction throws exception for different currencies")
        fun `subtraction throws exception for different currencies`() {
            val usd = Money(100.0, Currency.USD)
            val eur = Money(50.0, Currency.EUR)

            val exception = assertThrows<IllegalArgumentException> {
                usd - eur
            }

            assertTrue(exception.message!!.contains("Cannot subtract different currencies"))
        }

        @Test
        @DisplayName("Multiplication by double")
        fun `multiplication by double`() {
            val money = Money(100.0, Currency.USD)

            val result = money * 2.5

            assertEquals(Money(250.0, Currency.USD), result)
        }

        @Test
        @DisplayName("Multiplication by integer")
        fun `multiplication by integer`() {
            val money = Money(100.0, Currency.USD)

            val result = money * 3

            assertEquals(Money(300.0, Currency.USD), result)
        }

        @Test
        @DisplayName("Division by double")
        fun `division by double`() {
            val money = Money(100.0, Currency.USD)

            val result = money / 2.0

            assertEquals(Money(50.0, Currency.USD), result)
        }

        @Test
        @DisplayName("Division by integer")
        fun `division by integer`() {
            val money = Money(100.0, Currency.USD)

            val result = money / 4

            assertEquals(Money(25.0, Currency.USD), result)
        }

        @Test
        @DisplayName("Division by zero throws exception")
        fun `division by zero throws exception`() {
            val money = Money(100.0, Currency.USD)

            assertThrows<IllegalArgumentException> {
                money / 0.0
            }

            assertThrows<IllegalArgumentException> {
                money / 0
            }
        }

        @Test
        @DisplayName("Unary minus negates amount")
        fun `unary minus negates amount`() {
            val positive = Money(100.0, Currency.USD)
            val negative = -positive

            assertEquals(Money(-100.0, Currency.USD), negative)
        }

        @Test
        @DisplayName("Complex arithmetic operations")
        fun `complex arithmetic operations`() {
            val a = Money(100.0, Currency.USD)
            val b = Money(50.0, Currency.USD)

            val result = (a + b) * 2 - Money(100.0, Currency.USD)

            assertEquals(Money(200.0, Currency.USD), result)
        }
    }

    @Nested
    @DisplayName("Money Comparison")
    inner class MoneyComparison {

        @Test
        @DisplayName("Compare equal amounts")
        fun `compare equal amounts`() {
            val money1 = Money(100.0, Currency.USD)
            val money2 = Money(100.0, Currency.USD)

            assertEquals(0, money1.compareTo(money2))
            assertTrue(money1 == money2)
        }

        @Test
        @DisplayName("Compare different amounts")
        fun `compare different amounts`() {
            val smaller = Money(50.0, Currency.USD)
            val larger = Money(100.0, Currency.USD)

            assertTrue(smaller < larger)
            assertTrue(larger > smaller)
            assertTrue(smaller <= larger)
            assertTrue(larger >= smaller)
        }

        @Test
        @DisplayName("Comparison throws exception for different currencies")
        fun `comparison throws exception for different currencies`() {
            val usd = Money(100.0, Currency.USD)
            val eur = Money(100.0, Currency.EUR)

            val exception = assertThrows<IllegalArgumentException> {
                usd.compareTo(eur)
            }

            assertTrue(exception.message!!.contains("Cannot compare different currencies"))
        }

        @Test
        @DisplayName("Compare money with double")
        fun `compare money with double`() {
            val money = Money(100.0, Currency.USD)

            assertTrue(money.compareTo(50.0) > 0)
            assertTrue(money.compareTo(100.0) == 0)
            assertTrue(money.compareTo(150.0) < 0)
        }
    }

    @Nested
    @DisplayName("Edge Cases")
    inner class EdgeCases {

        @Test
        @DisplayName("Handle very large amounts")
        fun `handle very large amounts`() {
            val money = Money(999999999.99, Currency.USD)

            assertEquals("$999,999,999.99", money.formatted)
        }

        @Test
        @DisplayName("Handle very small amounts")
        fun `handle very small amounts`() {
            val money = Money(0.01, Currency.USD)

            assertEquals("$0.01", money.formatted)
        }

        @Test
        @DisplayName("Handle zero")
        fun `handle zero`() {
            val money = Money.zero()

            assertTrue(money.isZero)
            assertEquals("$0.00", money.formatted)
        }

        @Test
        @DisplayName("Handle negative zero")
        fun `handle negative zero`() {
            val money = Money(-0.0, Currency.USD)

            assertEquals("$0.00", money.formatted)
        }

        @Test
        @DisplayName("Handle amounts without decimals")
        fun `handle amounts without decimals`() {
            val money = Money(100.0, Currency.USD)

            assertEquals("$100.00", money.formatted)
        }

        @Test
        @DisplayName("Currency with zero decimal places")
        fun `currency with zero decimal places`() {
            val money = Money(1234.56, Currency.JPY)

            // JPY has 0 decimal places, should round/truncate
            assertEquals("¥1,235", money.formatted)
        }
    }

    @Nested
    @DisplayName("Money Equality")
    inner class MoneyEquality {

        @Test
        @DisplayName("Equal money amounts are equal")
        fun `equal money amounts are equal`() {
            val money1 = Money(100.0, Currency.USD)
            val money2 = Money(100.0, Currency.USD)

            assertEquals(money1, money2)
            assertEquals(money1.hashCode(), money2.hashCode())
        }

        @Test
        @DisplayName("Different amounts are not equal")
        fun `different amounts are not equal`() {
            val money1 = Money(100.0, Currency.USD)
            val money2 = Money(50.0, Currency.USD)

            assert(money1 != money2)
        }

        @Test
        @DisplayName("Different currencies are not equal")
        fun `different currencies are not equal`() {
            val usd = Money(100.0, Currency.USD)
            val eur = Money(100.0, Currency.EUR)

            assert(usd != eur)
        }
    }

    @Nested
    @DisplayName("Real-World Scenarios")
    inner class RealWorldScenarios {

        @Test
        @DisplayName("Calculate shopping cart total")
        fun `calculate shopping cart total`() {
            val item1 = Money(29.99, Currency.USD)
            val item2 = Money(15.50, Currency.USD)
            val item3 = Money(8.75, Currency.USD)

            val total = item1 + item2 + item3

            // Use formatted comparison to handle floating-point precision
            assertEquals("$54.24", total.formatted)
            // Also verify amount is close enough (within 0.01)
            assertEquals(54.24, total.amount, 0.01)
        }

        @Test
        @DisplayName("Calculate discount")
        fun `calculate discount`() {
            val price = Money(100.0, Currency.USD)
            val discount = price * 0.20 // 20% off

            val finalPrice = price - discount

            assertEquals(Money(80.0, Currency.USD), finalPrice)
        }

        @Test
        @DisplayName("Split bill equally")
        fun `split bill equally`() {
            val bill = Money(150.0, Currency.USD)
            val people = 3

            val perPerson = bill / people

            assertEquals(Money(50.0, Currency.USD), perPerson)
        }

        @Test
        @DisplayName("Calculate tax")
        fun `calculate tax`() {
            val price = Money(100.0, Currency.USD)
            val taxRate = 0.08 // 8% tax

            val tax = price * taxRate
            val total = price + tax

            assertEquals(Money(8.0, Currency.USD), tax)
            assertEquals(Money(108.0, Currency.USD), total)
        }

        @Test
        @DisplayName("Account balance operations")
        fun `account balance operations`() {
            var balance = Money(1000.0, Currency.USD)

            // Deposit
            balance = balance + Money(500.0, Currency.USD)
            assertEquals(Money(1500.0, Currency.USD), balance)

            // Withdrawal
            balance = balance - Money(200.0, Currency.USD)
            assertEquals(Money(1300.0, Currency.USD), balance)

            assertTrue(balance.isPositive)
        }
    }
}
