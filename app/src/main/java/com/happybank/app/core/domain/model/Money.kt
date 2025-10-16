package com.happybank.app.core.domain.model

import kotlin.math.abs

/**
 * Money value object
 * Represents a monetary amount with currency
 *
 * Immutable and type-safe representation of money with support for
 * arithmetic operations, formatting, and currency validation
 */
data class Money(
    val amount: Double,
    val currency: Currency
) : Comparable<Money> {

    /**
     * Formatted string representation with currency symbol
     * Examples: "$1,234.56", "€1.234,56", "¥1,234"
     */
    val formatted: String
        get() = formatAmount(amount, currency)

    /**
     * Compact format without currency symbol
     * Examples: "1,234.56", "1234"
     */
    val amountFormatted: String
        get() {
            val formatString = "%.${currency.decimalPlaces}f"
            val amountStr = formatString.format(amount)
            return addThousandsSeparator(amountStr)
        }

    /**
     * Display format with symbol and amount
     * Examples: "$1,234.56", "€1,234.56"
     */
    val displayText: String
        get() = "${currency.symbol}$amountFormatted"

    /**
     * Check if amount is positive
     */
    val isPositive: Boolean get() = amount > 0

    /**
     * Check if amount is negative
     */
    val isNegative: Boolean get() = amount < 0

    /**
     * Check if amount is zero
     */
    val isZero: Boolean get() = amount == 0.0

    /**
     * Absolute value
     */
    val absolute: Money
        get() = Money(abs(amount), currency)

    /**
     * Negate the amount
     */
    operator fun unaryMinus(): Money = Money(-amount, currency)

    /**
     * Add two money amounts
     * @throws IllegalArgumentException if currencies don't match
     */
    operator fun plus(other: Money): Money {
        requireSameCurrency(other, "add")
        return Money(amount + other.amount, currency)
    }

    /**
     * Subtract two money amounts
     * @throws IllegalArgumentException if currencies don't match
     */
    operator fun minus(other: Money): Money {
        requireSameCurrency(other, "subtract")
        return Money(amount - other.amount, currency)
    }

    /**
     * Multiply money by a scalar
     */
    operator fun times(multiplier: Double): Money {
        return Money(amount * multiplier, currency)
    }

    /**
     * Multiply money by an integer
     */
    operator fun times(multiplier: Int): Money {
        return Money(amount * multiplier, currency)
    }

    /**
     * Divide money by a scalar
     * @throws IllegalArgumentException if divisor is zero
     */
    operator fun div(divisor: Double): Money {
        require(divisor != 0.0) { "Cannot divide by zero" }
        return Money(amount / divisor, currency)
    }

    /**
     * Divide money by an integer
     * @throws IllegalArgumentException if divisor is zero
     */
    operator fun div(divisor: Int): Money {
        require(divisor != 0) { "Cannot divide by zero" }
        return Money(amount / divisor, currency)
    }

    /**
     * Compare money amounts
     * @throws IllegalArgumentException if currencies don't match
     */
    override fun compareTo(other: Money): Int {
        requireSameCurrency(other, "compare")
        return amount.compareTo(other.amount)
    }

    /**
     * Check if this money is greater than another
     */
    operator fun compareTo(other: Double): Int = amount.compareTo(other)

    /**
     * Ensure currencies match for operations
     */
    private fun requireSameCurrency(other: Money, operation: String) {
        require(currency == other.currency) {
            "Cannot $operation different currencies: ${currency.code} and ${other.currency.code}"
        }
    }

    override fun toString(): String = formatted

    companion object {
        /**
         * Zero money in specified currency
         */
        fun zero(currency: Currency = Currency.USD) = Money(0.0, currency)

        /**
         * Create money from amount and currency code
         */
        fun of(amount: Double, currencyCode: String): Money {
            return Money(amount, Currency.fromCode(currencyCode))
        }

        /**
         * Create money in USD
         */
        fun usd(amount: Double) = Money(amount, Currency.USD)

        /**
         * Create money in EUR
         */
        fun eur(amount: Double) = Money(amount, Currency.EUR)

        /**
         * Create money in GBP
         */
        fun gbp(amount: Double) = Money(amount, Currency.GBP)

        /**
         * Create money in JPY
         */
        fun jpy(amount: Double) = Money(amount, Currency.JPY)

        /**
         * Create money in PLN
         */
        fun pln(amount: Double) = Money(amount, Currency.PLN)

        /**
         * Format amount with currency symbol
         */
        private fun formatAmount(amount: Double, currency: Currency): String {
            // Normalize negative zero to positive zero
            val normalizedAmount = if (amount == 0.0) 0.0 else amount

            val formatString = "%.${currency.decimalPlaces}f"
            val amountStr = formatString.format(normalizedAmount)
            val withSeparators = addThousandsSeparator(amountStr)
            return "${currency.symbol}$withSeparators"
        }

        /**
         * Add thousands separator to number string
         * Examples: "1234.56" -> "1,234.56"
         */
        private fun addThousandsSeparator(value: String): String {
            val parts = value.split(".")
            val integerPart = parts[0]
            val decimalPart = if (parts.size > 1) parts[1] else ""

            // Handle negative numbers
            val isNegative = integerPart.startsWith("-")
            val absoluteInteger = if (isNegative) integerPart.substring(1) else integerPart

            // Add commas every 3 digits from right
            val formatted = absoluteInteger.reversed()
                .chunked(3)
                .joinToString(",")
                .reversed()

            val withSign = if (isNegative) "-$formatted" else formatted

            return if (decimalPart.isNotEmpty()) {
                "$withSign.$decimalPart"
            } else {
                withSign
            }
        }
    }
}

/**
 * Extension function to create Money from Double
 * Example: 100.0.usd() -> Money(100.0, Currency.USD)
 */
fun Double.usd() = Money.usd(this)

/**
 * Extension function to create Money from Int
 */
fun Int.usd() = Money.usd(this.toDouble())

/**
 * Extension function to create EUR money
 */
fun Double.eur() = Money.eur(this)

/**
 * Extension function to create EUR money from Int
 */
fun Int.eur() = Money.eur(this.toDouble())

/**
 * Extension function to create GBP money
 */
fun Double.gbp() = Money.gbp(this)

/**
 * Extension function to create JPY money
 */
fun Double.jpy() = Money.jpy(this)

/**
 * Extension function to create PLN money
 */
fun Double.pln() = Money.pln(this)
