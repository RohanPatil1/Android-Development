package com.rohan.triviatrack.data_models

data class TriviaList(
    val response_code: Int,
    val results: List<Result>
)