package com.happybank.app.mock

import android.content.Context
import android.util.Log
import fi.iki.elonen.NanoHTTPD
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Mock HTTP server using NanoHTTPD
 * Serves mock JSON responses from assets/mock/ folder
 * Only included in mock build flavor
 */
@Singleton
class MockServer @Inject constructor(
    private val context: Context
) : NanoHTTPD(PORT) {

    companion object {
        private const val TAG = "MockServer"
        const val PORT = 8080
        private const val MOCK_DELAY_MS = 500L // Simulate network latency
    }

    override fun serve(session: IHTTPSession): Response {
        val uri = session.uri
        Log.d(TAG, "Mock request: ${session.method} $uri")

        // Simulate network delay
        Thread.sleep(MOCK_DELAY_MS)

        return try {
            when {
                uri.startsWith("/api/v1/account/balance") -> {
                    serveJsonFromAssets("mock/balance.json")
                }
                uri.startsWith("/api/v1/account") -> {
                    serveJsonFromAssets("mock/account.json")
                }
                uri.startsWith("/api/v1/transactions") -> {
                    serveJsonFromAssets("mock/transactions.json")
                }
                else -> {
                    Log.w(TAG, "No mock data for: $uri")
                    newFixedLengthResponse(
                        Response.Status.NOT_FOUND,
                        "application/json",
                        """{"success":false,"message":"Mock endpoint not found: $uri"}"""
                    )
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error serving mock data", e)
            newFixedLengthResponse(
                Response.Status.INTERNAL_ERROR,
                "application/json",
                """{"success":false,"message":"Error: ${e.message}"}"""
            )
        }
    }

    private fun serveJsonFromAssets(assetPath: String): Response {
        return try {
            val json = context.assets.open(assetPath).bufferedReader().use { it.readText() }
            newFixedLengthResponse(
                Response.Status.OK,
                "application/json",
                json
            )
        } catch (e: IOException) {
            Log.e(TAG, "Failed to read asset: $assetPath", e)
            newFixedLengthResponse(
                Response.Status.NOT_FOUND,
                "application/json",
                """{"success":false,"message":"Asset not found: $assetPath"}"""
            )
        }
    }

    fun startServer() {
        try {
            start(NanoHTTPD.SOCKET_READ_TIMEOUT, false)
            Log.i(TAG, "✅ Mock server started on http://localhost:$PORT")
        } catch (e: IOException) {
            Log.e(TAG, "❌ Failed to start mock server", e)
        }
    }

    fun stopServer() {
        stop()
        Log.i(TAG, "Mock server stopped")
    }
}
