package com.rohan.triviatrack.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rohan.triviatrack.R
import com.rohan.triviatrack.data_models.Result

import com.rohan.triviatrack.data_models.TriviaList


class TriviaAdapter(val context: Context, var triviaQuesList: List<Result>) :
    RecyclerView.Adapter<TriviaAdapter.TriviaViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TriviaViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.quiz_item_layout, parent, false)
        return TriviaViewHolder(view)

    }

    class TriviaViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        var correctAns = ""
        private val questionText: TextView = view.findViewById(R.id.tvQuestion)
        private val ansA: Button = view.findViewById(R.id.tvAnswerA)
        private val ansB: Button = view.findViewById(R.id.tvAnswerB)
        private val ansC: Button = view.findViewById(R.id.tvAnswerC)
        private val ansD: Button = view.findViewById(R.id.tvAnswerD)

        fun setData(result: Result, position: Int) {
            correctAns = result.correct_answer
            questionText.text = result.question

            val allAnsList: List<String> = result.incorrect_answers
            val finalList = allAnsList.toMutableList()
            finalList.add(3, result.correct_answer)
            Log.d("All Ans", finalList.toString())
            finalList.shuffle()
            ansA.text = finalList[0]
            ansB.text = finalList[1]
            ansC.text = finalList[2]
            ansD.text = finalList[3]


        }

        fun setClickListeners() {
            ansA.setOnClickListener(this@TriviaViewHolder)
            ansB.setOnClickListener(this@TriviaViewHolder)
            ansC.setOnClickListener(this@TriviaViewHolder)
            ansD.setOnClickListener(this@TriviaViewHolder)
        }

        override fun onClick(v: View?) {


//            when(correctAns){
//                ansA.text->ansA.setBackgroundColor(Color.CYAN)
//                ansB.text->ansB.setBackgroundColor(Color.CYAN)
//                ansC.text->ansC.setBackgroundColor(Color.CYAN)
//                ansD.text->ansD.setBackgroundColor(Color.CYAN)
//                else -> {
//
//                }
//            }

            when (v!!.id) {
                R.id.tvAnswerA -> {
                    if (ansA.text == correctAns) {
                        Log.d("CHECK", "Correct Ans : $correctAns  ansA.text ${ansA.text}")

                        ansA.setBackgroundColor(Color.CYAN)
                    }
                }
                R.id.tvAnswerB -> {
                    if (ansB.text == correctAns) {
                        Log.d("CHECK", "Correct Ans : $correctAns  ansB.text ${ansB.text}")

                        ansB.setBackgroundColor(Color.CYAN)
                    }
                }
                R.id.tvAnswerC -> {
                    if (ansC.text == correctAns) {
                        Log.d("CHECK", "Correct Ans : $correctAns  ansC.text ${ansC.text}")

                        ansC.setBackgroundColor(Color.CYAN)
                    }
                }
                R.id.tvAnswerD -> {
                    if (ansD.text == correctAns) {
                        Log.d("CHECK", "Correct Ans : $correctAns  ansD.text ${ansD.text}")

                        ansD.setBackgroundColor(Color.CYAN)
                    }
                }

            }
        }
    }


    override fun onBindViewHolder(holder: TriviaViewHolder, position: Int) {
        val currResult = triviaQuesList[position]
        holder.setData(currResult, position)
        holder.setClickListeners()
    }


//    class TriviaDiffUtil : DiffUtil.ItemCallback<Result>() {
//        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
//            return oldItem.questionId == newItem.questionId
//        }
//
//        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
//            return oldItem == newItem
//        }
//
//    }

    override fun getItemCount(): Int = triviaQuesList.size

}