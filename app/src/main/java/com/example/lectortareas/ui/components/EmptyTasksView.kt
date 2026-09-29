package com.example.lectortareas.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.lectortareas.ui.theme.AppColors
import com.example.lectortareas.ui.theme.AppShapes

@Composable
fun EmptyTasksView(
    message: String = "La app A no tiene tareas guardadas.",
    isError: Boolean = false,
    modifier: Modifier = Modifier
) {
    if (isError) {
        Card(
            shape = AppShapes.CardShape,
            colors = CardDefaults.cardColors(containerColor = AppColors.DangerSoft),
            border = BorderStroke(1.dp, AppColors.Danger.copy(alpha = 0.25f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            modifier = modifier.fillMaxWidth()
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = AppColors.Danger,
                modifier = Modifier.padding(16.dp)
            )
        }
    } else {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 32.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.titleMedium,
                color = AppColors.InkFaint,
                textAlign = TextAlign.Center
            )
        }
    }
}