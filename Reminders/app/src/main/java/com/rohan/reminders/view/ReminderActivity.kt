package com.rohan.reminders.view

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.rohan.reminders.alarm_utils.AlarmUtils
import com.rohan.reminders.data_model.Reminder
import com.rohan.reminders.view.ui.theme.RemindersTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReminderActivity : ComponentActivity() {

    private val reminderViewModel: ReminderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RemindersTheme {
                Surface(color = MaterialTheme.colors.background) {
                    PageBody()
                }
            }
        }
    }


    @Composable
    fun PageBody() {
        Scaffold(
            backgroundColor = Color(0xFFFFDE03),
            topBar = {
                TopAppBar(
                    backgroundColor = Color(0xFF0336FF),
                    title = {
                        Text(text = "Reminders")
                    }
                )
            },
            floatingActionButton = {
                val openDialog = remember { mutableStateOf(false) }
                FloatingActionButton(
                    backgroundColor = Color(0xFFFF0266), onClick = {
                        openDialog.value = true

                    }) {
                    AddDialog(openDialog = openDialog)
                    Icon(Icons.Default.Add, contentDescription = "")
                }
            },


            ) {
            ReminderListView(reminderViewModel = reminderViewModel)
        }
    }


    @Composable
    fun AddDialog(openDialog: MutableState<Boolean>) {
        val title = remember { mutableStateOf("") }
        val description = remember { mutableStateOf("") }
        val time = remember { mutableStateOf("") }
        val localCont = LocalContext.current

        if (openDialog.value) {
            AlertDialog(onDismissRequest = {
                openDialog.value = false
            },
                title = {
                    Text(text = "Add Reminder")
                },
                text = {
                    //Body
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = title.value,
                            onValueChange = {
                                title.value = it
                            },
                            placeholder = {
                                Text("Enter title")
                            }, modifier = Modifier
                                .padding(8.dp)
                        )

                        OutlinedTextField(
                            value = description.value,
                            onValueChange = {
                                description.value = it
                            },
                            placeholder = {
                                Text("Enter description")
                            }, modifier = Modifier
                                .padding(8.dp)
                        )

                        OutlinedTextField(
                            value = time.value,
                            onValueChange = {
                                time.value = it
                            },
                            keyboardOptions =
                            KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            placeholder = {
                                Text("Enter time")
                            }, modifier = Modifier
                                .padding(8.dp)
                        )
                    }


                },
                confirmButton = {
                    OutlinedButton(onClick = {
                        insert(title, description, time)
                        setReminderAlarm(
                            localCont,
                            title.value,
                            description.value,
                            time = time.value.toInt()
                        )
                        openDialog.value = false
                    }) {
                        Text("Save")
                    }
                }
            )
        }
    }

    @Composable
    fun ReminderListView(reminderViewModel: ReminderViewModel) {
        LazyColumn {
            items(reminderViewModel.response.value) { r ->
                ReminderTile(reminder = r)
            }
        }
    }

    @Composable
    fun ReminderTile(reminder: Reminder) {
        val context = LocalContext.current
        Card(

            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            elevation = 2.dp,
            backgroundColor = Color.White,

            shape = RoundedCornerShape(8.dp)
        ) {

            Column(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Text(
                    reminder.title,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xff181818),
                    style = TextStyle(fontSize = 16.sp)
                )
                Text(
                    reminder.description,
                    color = Color(0xff181818),
                    style = TextStyle(fontSize = 12.sp)
                )
                Text(

                    reminder.time.toString() + " sec later",
                    modifier = Modifier
                        .align(Alignment.End),
                    fontWeight = FontWeight.ExtraLight,
                    style = TextStyle(color = Color.Black, fontSize = 12.sp)
                )
//                OutlinedButton(onClick = { cancelAlarm(context) }) {
//                                Text("Cancel alarm")
//                }
            }

        }
    }

    private fun setReminderAlarm(context: Context, title: String, description: String, time: Int) {
        val timeSec = System.currentTimeMillis() + time
        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmUtils::class.java)
        intent.putExtra("title", title)
        intent.putExtra("description", description)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeSec, pendingIntent)
        // Toast.makeText(this, "Alarm Set!", Toast.LENGTH_LONG).show()
    }

    private fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmUtils::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        alarmManager.cancel(pendingIntent)
    }


    private fun insert(
        title: MutableState<String>,
        description: MutableState<String>,
        time: MutableState<String>
    ) {
        lifecycleScope.launchWhenStarted {
            if (!TextUtils.isEmpty(title.value) && !TextUtils.isEmpty(description.value) && !TextUtils.isEmpty(
                    time.value
                )
            ) {
                reminderViewModel.insertReminder(
                    Reminder(
                        title = title.value,
                        description = description.value,
                        time = time.value.toInt()
                    )
                ).invokeOnCompletion {
                    Toast.makeText(this@ReminderActivity, "Reminder added!", Toast.LENGTH_LONG)
                        .show()
                }

            } else {
                Toast.makeText(this@ReminderActivity, "Invalid input", Toast.LENGTH_LONG).show()
            }
        }
    }


}


