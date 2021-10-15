package com.rohan.triviatrack.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rohan.triviatrack.api.TriviaService
import com.rohan.triviatrack.data_models.TriviaList
import com.rohan.triviatrack.db.TriviaDB
import com.rohan.triviatrack.utils.NetworkUtils

class TriviaRepository(
    private val triviaService: TriviaService,
    private val triviaDB: TriviaDB,
    private val context: Context
) {


    private val triviaLiveData = MutableLiveData<TriviaList>()

    //Publicly accessible LiveData
    val triviaQues: LiveData<TriviaList>
        get() = triviaLiveData

    suspend fun getTriviaQues(amount: Int, category: Int?, difficulty: String?, type: String?) {

        if (NetworkUtils.isInternetAvailable(context)) {
            val result = triviaService.getTriviaQues(amount, category, difficulty, type)
            if (result?.body() != null) {

             //   triviaDB.triviaDAO().insertQues(result.body()!!.results)
                triviaLiveData.postValue(result.body())
            }
        }
//        else {
//            val ques = triviaDB.triviaDAO().getQues()
//            val quesList = TriviaList(0, ques)
//            triviaLiveData.postValue(quesList)
//        }
    }
}