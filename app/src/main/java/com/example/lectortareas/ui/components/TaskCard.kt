package com.example.lectortareas.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.lectortareas.data.SharedTask
import com.example.lectortareas.ui.theme.AppColors
import com.example.lectortareas.ui.theme.AppShapes
import com.example.lectortareas.ui.theme.AppTextStyles
import com.example.lectortareas.ui.utils.DateUtils

@Composable
fun TaskCard(
    task: SharedTask,
    onView: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = AppShapes.CardShape,
        colors = CardDefaults.cardColors(containerColor = AppColors.CardWhite),
        border = BorderStroke(1.dp, AppColors.Line),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, top = 12.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(AppColors.AccentSoft),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Assignment,
                    contentDescription = "Tarea",
                    tint = AppColors.AccentStrong,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = DateUtils.formatDateTime(task.createdAt),
                    style = AppTextStyles.TaskDateCard,
                    color = AppColors.AccentStrong
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = task.title.ifBlank { "Sin título" },
                    style = AppTextStyles.TaskTitleCard,
                    color = AppColors.Ink,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textDecoration = if (task.completed) TextDecoration.LineThrough else TextDecoration.None
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Button(
                onClick = onView,
                shape = AppShapes.PillShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.AccentSoft,
                    contentColor = AppColors.AccentStrong
                ),
                elevation = null,
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(text = "Consultar", style = AppTextStyles.ButtonLabel)
            }
        }
    }
}