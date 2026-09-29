package com.example.lectortareas.data

data class SharedTask(
    val id: Int,
    val title: String,
    val description: String,
    val completed: Boolean,
    val createdAt: Long
)