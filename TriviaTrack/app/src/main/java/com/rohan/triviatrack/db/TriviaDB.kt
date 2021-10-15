package com.rohan.triviatrack.db

import android.content.Context
import androidx.room.*
import com.rohan.triviatrack.data_models.Result
import com.rohan.triviatrack.utils.MyListConverter

@Database(entities = [Result::class], version = 1,exportSchema = false)
@TypeConverters( MyListConverter::class)
abstract class TriviaDB : RoomDatabase() {

    abstract fun triviaDAO(): TriviaDAO

    companion object {

        @Volatile
        private var INSTANCE: TriviaDB? = null

        fun getDatabase(context: Context): TriviaDB {
            synchronized(this) {
                INSTANCE = Room.databaseBuilder(context, TriviaDB::class.java, "triviaDB").build()
            }
            return INSTANCE!!
        }


    }


}
