package com.rohan.reminders.view

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rohan.reminders.data.ReminderRepository
import com.rohan.reminders.data_model.Reminder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(private val repository: ReminderRepository) :
    ViewModel() {

    val response: MutableState<List<Reminder>> = mutableStateOf(listOf())

    init {
        getAllReminders()
    }

    fun insertReminder(reminder: Reminder) = viewModelScope.launch {
        repository.insert(reminder)
    }

    private fun getAllReminders() = viewModelScope.launch {
        repository.getAllReminders().catch { e ->
            Log.d("main", "getAllReminders : ${e.message}")
        }.collect {
            response.value = it
        }
    }

}