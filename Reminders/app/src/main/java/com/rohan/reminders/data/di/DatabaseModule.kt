package com.rohan.reminders.data.di

import android.app.Application
import androidx.room.Room
import com.rohan.reminders.BaseApplication
import com.rohan.reminders.data.ReminderDao
import com.rohan.reminders.data.ReminderDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    @Provides
    @Singleton
    fun provideDatabase(application: Application): ReminderDatabase =
        Room.databaseBuilder(application, ReminderDatabase::class.java, "reminderDatabase")
            .fallbackToDestructiveMigration()
            .build()


    @Provides
    @Singleton
    fun provideDao(reminderDatabase: ReminderDatabase): ReminderDao = reminderDatabase.getDao()

}