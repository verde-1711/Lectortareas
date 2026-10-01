package com.example.lectortareas.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.lectortareas.ui.theme.AppColors
import com.example.lectortareas.ui.theme.AppShapes
import com.example.lectortareas.ui.theme.AppTextStyles

@Composable
fun PermissionNotice(
    modifier: Modifier = Modifier
) {
    Card(
        shape = AppShapes.CardShape,
        colors = CardDefaults.cardColors(containerColor = AppColors.CardWhite),
        border = BorderStroke(1.dp, AppColors.Line),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(AppColors.AccentSoft, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Lock,
                    contentDescription = null,
                    tint = AppColors.Accent,
                    modifier = Modifier.size(30.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Acceso a tus tareas",
                style = AppTextStyles.SectionTitle,
                color = AppColors.Ink,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Concede el permiso para ver las tareas guardadas en la aplicación de tareas.",
                style = AppTextStyles.Description,
                color = AppColors.InkSoft,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Si no aparece el diálogo, instala primero la aplicación de tareas y reinstala esta app.",
                style = AppTextStyles.EmptyMessage,
                color = AppColors.InkFaint,
                textAlign = TextAlign.Center
            )
        }
    }
}