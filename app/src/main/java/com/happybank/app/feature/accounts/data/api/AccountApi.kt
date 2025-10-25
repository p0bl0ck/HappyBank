package com.happybank.app.feature.accounts.data.api

import retrofit2.http.GET
import retrofit2.http.Header

/**
 * Account API interface
 * Defines endpoints for account-related operations
 */
interface AccountApi {

    /**
     * Get user account information
     * @param authorization Bearer token for authentication
     * @return Account details
     */
    @GET("api/v1/account")
    suspend fun getAccount(
        @Header("Authorization") authorization: String
    ): AccountDto

    /**
     * Get account balance
     * @param authorization Bearer token for authentication
     * @return Balance amount
     */
    @GET("api/v1/account/balance")
    suspend fun getBalance(
        @Header("Authorization") authorization: String
    ): Double
}
