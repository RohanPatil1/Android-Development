package com.rohan.triviatrack.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rohan.triviatrack.repository.TriviaRepository

//Creates viewModels
class TriviaViewModelFactory(
    private val repository: TriviaRepository,
    private val   amount: Int,
    private val  category: Int,
    private val  difficulty: String,
    private val type: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TriviaViewModel(repository,amount,category,difficulty,type) as T
    }
}