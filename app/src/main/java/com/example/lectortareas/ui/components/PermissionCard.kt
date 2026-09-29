package com.example.lectortareas.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.lectortareas.ui.theme.AppColors
import com.example.lectortareas.ui.theme.AppShapes

@Composable
fun PermissionCard(
    hasPermission: Boolean,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val containerColor =
        if (hasPermission) AppColors.AccentSoft else AppColors.DangerSoft

    val contentColor =
        if (hasPermission) AppColors.AccentStrong else AppColors.Danger

    Card(
        shape = AppShapes.CardShape,
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        border = BorderStroke(
            1.dp,
            contentColor.copy(alpha = 0.25f)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = if (hasPermission) {
                    "Permiso concedido"
                } else {
                    "Sin permiso"
                },
                style = MaterialTheme.typography.titleSmall,
                color = contentColor
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (hasPermission) {
                Button(
                    onClick = onButtonClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = AppShapes.PillShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.CardWhite,
                        contentColor = AppColors.Danger
                    ),
                    border = BorderStroke(
                        1.dp,
                        AppColors.Danger
                    ),
                    elevation = null
                ) {
                    Text(
                        text = "Quitar permiso",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            } else {
                Button(
                    onClick = onButtonClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = AppShapes.PillShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.Accent,
                        contentColor = Color.White
                    ),
                    elevation = null
                ) {
                    Text(
                        text = "Pedir permiso",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}