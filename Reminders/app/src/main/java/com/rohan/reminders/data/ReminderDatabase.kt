package com.rohan.reminders.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rohan.reminders.data_model.Reminder

@Database(entities = [Reminder::class], version = 1, exportSchema = false)
abstract class ReminderDatabase : RoomDatabase() {

        abstract fun getDao():ReminderDao


}