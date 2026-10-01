package com.example.lectortareas.data

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object TasksResolver {

    const val READ_PERMISSION = "com.example.persistencia.permission.READ_TASKS"
    private val TASKS_URI: Uri = Uri.parse("content://com.example.persistencia.provider/tasks")

    fun hasPermission(context: Context): Boolean =
        ContextCompat.checkSelfPermission(context, READ_PERMISSION) == PackageManager.PERMISSION_GRANTED

    suspend fun readTasks(context: Context): Result<List<SharedTask>> = withContext(Dispatchers.IO) {
        try {
            val cursor = context.contentResolver.query(
                TASKS_URI, null, null, null, "fecha_creacion DESC"
            ) ?: return@withContext Result.failure(
                IllegalStateException("No se pudo conectar con la aplicación de tareas. Ábrela y vuelve a intentar")
            )

            cursor.use { c ->
                val idIdx = c.getColumnIndexOrThrow("id")
                val titleIdx = c.getColumnIndexOrThrow("titulo")
                val descIdx = c.getColumnIndexOrThrow("descripcion")
                val doneIdx = c.getColumnIndexOrThrow("estado_completado")
                val dateIdx = c.getColumnIndexOrThrow("fecha_creacion")

                val tasks = mutableListOf<SharedTask>()
                while (c.moveToNext()) {
                    tasks.add(
                        SharedTask(
                            id = c.getInt(idIdx),
                            title = c.getString(titleIdx) ?: "",
                            description = c.getString(descIdx) ?: "",
                            completed = c.getInt(doneIdx) == 1,
                            createdAt = c.getLong(dateIdx)
                        )
                    )
                }
                Result.success(tasks)
            }
        } catch (e: SecurityException) {
            Result.failure(e)
        } catch (e: IllegalArgumentException) {
            Result.failure(
                IllegalStateException("La aplicación de tareas no devolvió los datos esperados: ${e.message}")
            )
        }
    }
}