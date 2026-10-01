package com.example.lectortareas.ui.screens

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.lectortareas.data.SharedTask
import com.example.lectortareas.data.TasksResolver
import com.example.lectortareas.ui.components.EmptyTasksView
import com.example.lectortareas.ui.components.PermissionCard
import com.example.lectortareas.ui.components.PermissionNotice
import com.example.lectortareas.ui.components.TaskBottomNavigation
import com.example.lectortareas.ui.components.TaskListContent
import com.example.lectortareas.ui.theme.AppColors
import com.example.lectortareas.ui.theme.AppTextStyles

@Composable
fun TasksScreen() {
    val context = LocalContext.current
    val activity = context as? ComponentActivity

    var hasPermission by remember { mutableStateOf(TasksResolver.hasPermission(context)) }
    var result by remember { mutableStateOf<Result<List<SharedTask>>?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var reloadKey by remember { mutableIntStateOf(0) }
    var selectedId by rememberSaveable { mutableStateOf<Int?>(null) }
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    var deniedForGood by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        deniedForGood = !granted && activity != null &&
                !activity.shouldShowRequestPermissionRationale(TasksResolver.READ_PERMISSION)
        reloadKey++
    }

    // Cada vez que la app vuelve a primer plano se revisa el permiso y se recargan las tareas
    DisposableEffect(activity) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) reloadKey++
        }
        activity?.lifecycle?.addObserver(observer)
        onDispose { activity?.lifecycle?.removeObserver(observer) }
    }

    LaunchedEffect(reloadKey) {
        hasPermission = TasksResolver.hasPermission(context)
        if (hasPermission) {
            deniedForGood = false
            isLoading = true
            result = TasksResolver.readTasks(context)
            isLoading = false
        } else {
            result = null
            selectedId = null
        }
    }

    fun openAppSettings() {
        context.startActivity(
            Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", context.packageName, null)
            )
        )
    }

    fun onPermissionButtonClick() {
        if (hasPermission || deniedForGood) {
            openAppSettings()
        } else {
            permissionLauncher.launch(TasksResolver.READ_PERMISSION)
        }
    }

    val currentResult = result
    val tasks = currentResult?.getOrNull()
    val selectedTask = tasks?.firstOrNull { it.id == selectedId }

    // La lista se lee una sola vez; cada pestaña la filtra según el campo completed
    val allTasks = tasks.orEmpty()
    val pendingTasks = remember(allTasks) { allTasks.filter { it.completed == false } }
    val completedTasks = remember(allTasks) { allTasks.filter { it.completed == true } }

    val showTabs = hasPermission && currentResult?.isSuccess == true

    if (selectedTask != null) {
        BackHandler { selectedId = null }
        TaskDetailScreen(task = selectedTask, onBack = { selectedId = null })
    } else if (showTabs) {
        Scaffold(
            containerColor = AppColors.Background,
            bottomBar = {
                TaskBottomNavigation(
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
            ) {
                TasksHeader(
                    hasPermission = hasPermission,
                    onPermissionButtonClick = { onPermissionButtonClick() }
                )

                when (selectedTab) {
                    0 -> TaskListContent(
                        title = "Todas",
                        tasks = allTasks,
                        emptyMessage = "La aplicación de tareas no tiene tareas guardadas.",
                        onViewTask = { selectedId = it.id },
                        modifier = Modifier.weight(1f)
                    )

                    1 -> TaskListContent(
                        title = "Pendientes",
                        tasks = pendingTasks,
                        emptyMessage = "No tienes tareas pendientes",
                        onViewTask = { selectedId = it.id },
                        modifier = Modifier.weight(1f)
                    )

                    else -> TaskListContent(
                        title = "Completadas",
                        tasks = completedTasks,
                        emptyMessage = "No tienes tareas completadas",
                        onViewTask = { selectedId = it.id },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 16.dp)
        ) {
            TasksHeader(
                hasPermission = hasPermission,
                onPermissionButtonClick = { onPermissionButtonClick() }
            )

            when {
                !hasPermission -> PermissionNotice()


                currentResult == null -> {
                    if (isLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = AppColors.Accent)
                        }
                    }
                }

                currentResult.isFailure -> {
                    val error = currentResult.exceptionOrNull()
                    EmptyTasksView(
                        message = if (error is SecurityException) {
                            "No se pudo leer: el permiso no está activo. Vuelve a pedirlo."
                        } else {
                            error?.message ?: "No se pudieron leer las tareas."
                        },
                        isError = true
                    )
                }

                else -> Unit
            }
        }
    }
}

/** Título y tarjeta de permiso: quedan fijos arriba en todas las pestañas. */
@Composable
private fun TasksHeader(
    hasPermission: Boolean,
    onPermissionButtonClick: () -> Unit
) {
    Column {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Lector Tareas",
            style = AppTextStyles.ScreenTitle,
            color = AppColors.Ink
        )
        Spacer(modifier = Modifier.height(16.dp))

        PermissionCard(
            hasPermission = hasPermission,
            onButtonClick = onPermissionButtonClick
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}