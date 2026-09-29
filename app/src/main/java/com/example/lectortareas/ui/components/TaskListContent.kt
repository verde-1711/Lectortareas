package com.example.lectortareas.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lectortareas.data.SharedTask
import com.example.lectortareas.ui.theme.AppColors
import com.example.lectortareas.ui.theme.AppTextStyles

@Composable
fun TaskListContent(
    title: String,
    tasks: List<SharedTask>,
    emptyMessage: String,
    onViewTask: (SharedTask) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = title,
            style = AppTextStyles.SectionTitle,
            color = AppColors.Ink
        )
        Spacer(modifier = Modifier.height(12.dp))

        if (tasks.isEmpty()) {
            EmptyTasksView(message = emptyMessage)
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                items(tasks, key = { it.id }) { task ->
                    TaskCard(task = task, onView = { onViewTask(task) })
                }
            }
        }
    }
}