package com.rohan.reminders.data

import com.rohan.reminders.data_model.Reminder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ReminderRepository @Inject constructor(private val dao: ReminderDao) {
    suspend fun insert(reminder: Reminder) = withContext(Dispatchers.IO) {
        dao.insert(reminder)
    }

    fun getAllReminders(): Flow<List<Reminder>> = dao.getAllReminders()

}