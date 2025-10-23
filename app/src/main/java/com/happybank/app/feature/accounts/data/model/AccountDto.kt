package com.happybank.app.feature.accounts.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data Transfer Object for Account
 * Represents account data from API response
 */
@Serializable
data class AccountDto(
    @SerialName("account_id")
    val accountId: String,

    @SerialName("account_number")
    val accountNumber: String,

    @SerialName("account_type")
    val accountType: String,

    @SerialName("balance")
    val balance: Double,

    @SerialName("currency")
    val currency: String = "USD",

    @SerialName("user_name")
    val userName: String,

    @SerialName("created_at")
    val createdAt: String? = null
)
