package com.rohan.reminders.data_model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "reminderTable")
data class Reminder(
    val title: String, val description: String, val time: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}

