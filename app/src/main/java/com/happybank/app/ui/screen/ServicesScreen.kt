package com.happybank.app.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.happybank.app.ui.theme.HappyBankTheme

data class BankingService(
    val id: Int,
    val title: String,
    val description: String,
    val icon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicesScreen(
    onNavigateBack: () -> Unit = {}
) {
    val services = listOf(
        BankingService(
            id = 1,
            title = "Account Management",
            description = "View and manage your bank accounts",
            icon = Icons.Default.AccountBalance
        ),
        BankingService(
            id = 2,
            title = "Money Transfer",
            description = "Send money to friends and family",
            icon = Icons.Default.Send
        ),
        BankingService(
            id = 3,
            title = "Bill Payments",
            description = "Pay your bills on time, every time",
            icon = Icons.Default.Receipt
        ),
        BankingService(
            id = 4,
            title = "Investment Services",
            description = "Grow your wealth with our investment options",
            icon = Icons.Default.TrendingUp
        ),
        BankingService(
            id = 5,
            title = "Loans & Credit",
            description = "Apply for loans and credit cards",
            icon = Icons.Default.CreditCard
        ),
        BankingService(
            id = 6,
            title = "Insurance",
            description = "Protect what matters most to you",
            icon = Icons.Default.Security
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Banking Services",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(services) { service ->
                ServiceCard(service = service)
            }
        }
    }
}

@Composable
private fun ServiceCard(
    service: BankingService,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = { /* TODO: Navigate to service details */ }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = service.icon,
                contentDescription = service.title,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = service.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = service.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Go to ${service.title}",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ServicesScreenPreview() {
    HappyBankTheme {
        ServicesScreen()
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ServicesScreenDarkPreview() {
    HappyBankTheme {
        ServicesScreen()
    }
}
