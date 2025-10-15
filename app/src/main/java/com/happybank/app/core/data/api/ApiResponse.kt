package com.happybank.app.core.data.api

import kotlinx.serialization.Serializable

/**
 * Generic API response wrapper
 * Used to standardize API responses across the application
 */
@Serializable
data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val message: String? = null,
    val errorCode: String? = null
)

/**
 * Sealed class representing API result states
 */
sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val message: String, val code: String? = null) : ApiResult<Nothing>()
    data object Loading : ApiResult<Nothing>()
}
