package com.rohan.triviatrack

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.rohan.triviatrack.api.RetrofitHelper
import com.rohan.triviatrack.api.TriviaService
import com.rohan.triviatrack.db.TriviaDB
import com.rohan.triviatrack.repository.TriviaRepository
import com.rohan.triviatrack.view_models.TriviaViewModel
import com.rohan.triviatrack.view_models.TriviaViewModelFactory

class TriviaApplication : Application() {

    lateinit var triviaRepository: TriviaRepository
    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {

        val triviaService = RetrofitHelper.getInstance().create(TriviaService::class.java)
        val triviaDB = TriviaDB.getDatabase(applicationContext)
         triviaRepository = TriviaRepository(triviaService, triviaDB,applicationContext)

    }
}