package com.example.lectortareas.ui.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {

    private val dateTimeFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    fun formatDateTime(millis: Long): String = dateTimeFormat.format(Date(millis))
}