package com.example.lectortareas.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.PendingActions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.lectortareas.data.SharedTask
import com.example.lectortareas.ui.theme.AppColors
import com.example.lectortareas.ui.theme.AppShapes
import com.example.lectortareas.ui.theme.AppTextStyles
import com.example.lectortareas.ui.utils.DateUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    task: SharedTask,
    onBack: () -> Unit
) {
    Scaffold(
        containerColor = AppColors.Background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Consultar Tarea",
                        style = AppTextStyles.SectionTitle,
                        color = AppColors.Ink
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = AppColors.Ink
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = AppColors.Background)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Card(
                shape = AppShapes.CardShape,
                colors = CardDefaults.cardColors(containerColor = AppColors.CardWhite),
                border = BorderStroke(1.dp, AppColors.Line),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {

                    // Fecha: monoespaciada, con ícono de calendario
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.CalendarToday,
                            contentDescription = null,
                            tint = AppColors.Accent,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = DateUtils.formatDateTime(task.createdAt),
                            style = AppTextStyles.TaskDate,
                            color = AppColors.Accent
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Título: serif, grande y en negrita
                    Text(
                        text = task.title.ifBlank { "Sin título" },
                        style = AppTextStyles.TaskTitle,
                        color = AppColors.Ink,
                        textDecoration = if (task.completed) TextDecoration.LineThrough else TextDecoration.None
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Estado: píldora con ícono y letras separadas
                    val badgeBg = if (task.completed) AppColors.AccentSoft else AppColors.DangerSoft
                    val badgeColor = if (task.completed) AppColors.AccentStrong else AppColors.Danger
                    Row(
                        modifier = Modifier
                            .clip(AppShapes.PillShape)
                            .background(badgeBg)
                            .padding(horizontal = 14.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = if (task.completed) Icons.Outlined.CheckCircle else Icons.Outlined.PendingActions,
                            contentDescription = null,
                            tint = badgeColor,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = if (task.completed) "COMPLETADA" else "PENDIENTE",
                            style = AppTextStyles.StatusBadge,
                            color = badgeColor
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(AppColors.Line)
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    // Descripción: etiqueta pequeña y texto cómodo de leer
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.Description,
                            contentDescription = null,
                            tint = AppColors.InkFaint,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "DESCRIPCIÓN",
                            style = AppTextStyles.SectionLabel,
                            color = AppColors.InkFaint
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = if (task.description.isNotBlank()) task.description else "Sin descripción.",
                        style = AppTextStyles.Description,
                        color = if (task.description.isNotBlank()) AppColors.InkSoft else AppColors.InkFaint
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}