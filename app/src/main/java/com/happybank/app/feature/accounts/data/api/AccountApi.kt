package com.happybank.app.feature.accounts.data.api

import com.happybank.app.core.data.api.ApiResponse
import com.happybank.app.feature.accounts.data.model.AccountDto
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
     * @return Account details wrapped in ApiResponse
     */
    @GET("api/v1/account")
    suspend fun getAccount(
        @Header("Authorization") authorization: String
    ): ApiResponse<AccountDto>

    /**
     * Get account balance
     * @param authorization Bearer token for authentication
     * @return Balance information
     */
    @GET("api/v1/account/balance")
    suspend fun getBalance(
        @Header("Authorization") authorization: String
    ): ApiResponse<Double>
}
