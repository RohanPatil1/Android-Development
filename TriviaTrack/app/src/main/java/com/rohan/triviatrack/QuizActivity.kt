package com.rohan.triviatrack

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rohan.triviatrack.adapter.TriviaAdapter
import com.rohan.triviatrack.view_models.TriviaViewModel
import com.rohan.triviatrack.view_models.TriviaViewModelFactory

class QuizActivity : AppCompatActivity() {
    private lateinit var triviaViewModel: TriviaViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)


        val repository = (application as TriviaApplication).triviaRepository
        val quizRecyclerView = findViewById<RecyclerView>(R.id.quizRecyclerView)


        val amount: Int = intent.getIntExtra("amount", 11)
        val category: Int = intent.getIntExtra("category", 11)
        val difficulty: String? = intent.getStringExtra("difficulty")
        val type: String? = intent.getStringExtra("type")



        triviaViewModel = ViewModelProvider(
            this,
            TriviaViewModelFactory(repository, amount, category, difficulty!!, "multiple")
        ).get(TriviaViewModel::class.java)

        val triviaAdapter = TriviaAdapter(this, listOf())
        quizRecyclerView.layoutManager = LinearLayoutManager(this)
        quizRecyclerView.adapter = triviaAdapter

        triviaViewModel.triviaQues.observe(this, Observer {
            Log.d("APICHECK", it.results.toString())
            triviaAdapter.triviaQuesList = triviaViewModel.triviaQues.value!!.results
            triviaAdapter.notifyDataSetChanged()
        })


    }
}