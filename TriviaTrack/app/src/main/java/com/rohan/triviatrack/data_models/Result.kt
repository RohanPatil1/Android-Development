package com.rohan.triviatrack.data_models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "trivia")

data class Result(
    @PrimaryKey(autoGenerate = true)
    val questionId: Int,
    val category: String,
    val correct_answer: String,
    val difficulty: String,
    val incorrect_answers: List<String>,
    val question: String,
    val type: String
)
