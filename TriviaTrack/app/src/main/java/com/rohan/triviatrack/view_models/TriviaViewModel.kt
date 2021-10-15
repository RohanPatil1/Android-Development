package com.rohan.triviatrack.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rohan.triviatrack.data_models.TriviaList
import com.rohan.triviatrack.repository.TriviaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TriviaViewModel(
    private val repository: TriviaRepository,
    private val     amount: Int,
    private val  category: Int,
    private val difficulty: String,
    private val   type: String
) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getTriviaQues(amount, category , difficulty, type)
        }
    }

    val triviaQues: LiveData<TriviaList>
        get() = repository.triviaQues

}