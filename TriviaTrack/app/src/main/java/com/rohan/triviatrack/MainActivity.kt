package com.rohan.triviatrack

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rohan.triviatrack.api.RetrofitHelper
import com.rohan.triviatrack.api.TriviaService
import com.rohan.triviatrack.repository.TriviaRepository
import com.rohan.triviatrack.view_models.TriviaViewModel
import com.rohan.triviatrack.view_models.TriviaViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import android.widget.ArrayAdapter
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner

class MainActivity : AppCompatActivity() {

    lateinit var categoryAdapter: ArrayAdapter<CharSequence>
    lateinit var diffAdapter: ArrayAdapter<CharSequence>
//    lateinit var typeAdapter: ArrayAdapter<CharSequence>

    var categoryValue: Int = 21
    var diffValue: String = "hard"
    var typeValue: String = "multiple"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpSpinners()
        val startBtn: Button = findViewById(R.id.startBtn)
        startBtn.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java).apply {
                putExtra("amount", 10)
                putExtra("category", categoryValue)
                putExtra("difficulty", diffValue)
                putExtra("type", typeValue)
            }
            startActivity(intent)
        }
    }


    private fun setUpSpinners() {
        val categorySpinner: Spinner = findViewById(R.id.categorySpinner)
        val diffSpinner: Spinner = findViewById(R.id.difficultySpinner)
//        val typeSpinner: Spinner = findViewById(R.id.typeSpinner)

        categoryAdapter = ArrayAdapter<CharSequence>(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.category_options_list)
        )
        categoryAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        categorySpinner.adapter = categoryAdapter

        categorySpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {

                    0 -> categoryValue = 9
                    1 -> categoryValue = 10
                    2 -> categoryValue = 11
                    3 -> categoryValue = 12
                    4 -> categoryValue = 13
                    5 -> categoryValue = 14
                    6 -> categoryValue = 15
                    7 -> categoryValue = 16
                    8 -> categoryValue = 17
                    9 -> categoryValue = 18
                    10 -> categoryValue = 19
                    11 -> categoryValue = 20
                    12 -> categoryValue = 21
                    13 -> categoryValue = 22
                    14 -> categoryValue = 23
                    15 -> categoryValue = 24
                    16 -> categoryValue = 25
                    17 -> categoryValue = 26
                    18 -> categoryValue = 27
                    19 -> categoryValue = 28
                }
            }

        }


        diffAdapter = ArrayAdapter<CharSequence>(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.difficulty_options_list)
        )
        diffAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        diffSpinner.adapter = diffAdapter

        diffSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> diffValue = ""
                    1 -> diffValue = "easy"
                    2 -> diffValue = "medium"
                    3 -> diffValue = "hard"
                }
            }

        }

        //TODO
//        typeAdapter = ArrayAdapter<CharSequence>(
//            this,
//            R.layout.support_simple_spinner_dropdown_item,
//            resources.getStringArray(R.array.type_options_list)
//        )
//        typeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
//        typeSpinner.adapter = typeAdapter


    }
}