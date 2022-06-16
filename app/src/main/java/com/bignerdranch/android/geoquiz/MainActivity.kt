package com.bignerdranch.android.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.bignerdranch.android.geoquiz.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

private const val TAG= "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val questionBank= listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true))

    private var currentIndex=0




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /** setContentView(R.layout.activity_main) **/
        Log.d(TAG, "onCreate(Bundle?) called")
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /*trueButton.setOnClickListener { view: View ->
           /* Toast.makeText(this, R.string.correct_toast,
                Toast.LENGTH_SHORT).show()*/
            Snackbar.make(view,
                R.string.correct_toast,
                Snackbar.LENGTH_SHORT).show()
        }*/
        binding.trueButton.setOnClickListener {  view:View->
            checkAnswer(true, view)
        }
        binding.falseButton.setOnClickListener { view:View->
            checkAnswer(false, view)
        }
        binding.nextButton.setOnClickListener {
            currentIndex=(currentIndex+1)%questionBank.size
            updateQuestion()
        }

        binding.backButton.setOnClickListener {  view:View->
            if(currentIndex==0)
                currentIndex=6
            currentIndex=(currentIndex-1)%questionBank.size
            updateQuestion()
        }

        binding.questionTextView.setOnClickListener{ view:View ->
            currentIndex=(currentIndex+1)%questionBank.size
            updateQuestion()
        }
        updateQuestion()
    }

    override fun onStart(){
        super.onStart()
        Log.d(TAG,"onStart() called")
    }

    override fun onResume(){
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion(){
        val questionTextResId=questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean,view:View){
        val correctAnswer=questionBank[currentIndex].answer

        val messageResId=if(userAnswer==correctAnswer){
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Snackbar.make(view,
            messageResId,
            Snackbar.LENGTH_SHORT).show()
    }
}