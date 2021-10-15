package com.rohan.triviatrack.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rohan.triviatrack.data_models.Result


@Dao
interface TriviaDAO {

    @Insert
    suspend fun insertQues(ques: List<Result>)

    @Query("SELECT * FROM trivia")
    suspend fun getQues(): List<Result>

}