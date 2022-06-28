package com.bignerdranch.android.geoquiz

import android.app.Activity
import android.content.Intent
import android.icu.number.NumberFormatter
import android.icu.number.Precision
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.bignerdranch.android.geoquiz.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.math.RoundingMode
import java.text.DecimalFormat

private const val TAG= "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val quizViewModel: QuizViewModel by viewModels()
    private val cheatLauncher=registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if(result.resultCode== Activity.RESULT_OK){
            quizViewModel.isCheater=
                result.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false)?:false
        }
    }

    private var aciertos=0




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /** setContentView(R.layout.activity_main) **/
        Log.d(TAG, "onCreate(Bundle?) called")
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        binding.trueButton.setOnClickListener {  view:View->
            checkAnswer(true, view)
            checkBotonesRespuesta()

        }
        binding.falseButton.setOnClickListener { view:View->
            checkAnswer(false, view)
            checkBotonesRespuesta()

        }
        binding.nextButton.setOnClickListener { view:View->
            /*if(currentIndex==(questionBank.size-1))
                mostrarPorcentaje(view)*/
            /*currentIndex=(currentIndex+1)%questionBank.size*/
            quizViewModel.movetoNext()
            updateQuestion()
            checkBotonesRespuesta()

        }

        binding.chetButton.setOnClickListener {
            /*val intent=Intent(this, CheatActivity::class.java)*/
            val answerIsTrue=quizViewModel.currentQuestionAnswer
            val intent= CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            /*startActivity(intent)*/
            cheatLauncher.launch(intent)
        }

        binding.backButton.setOnClickListener {  view:View->
          /*  if(currentIndex==0)
                currentIndex=6
            currentIndex=(currentIndex-1)%questionBank.size*/
            updateQuestion()
            checkBotonesRespuesta()
        }

        binding.questionTextView.setOnClickListener{ view:View ->
            /*currentIndex=(currentIndex+1)%questionBank.size*/
            quizViewModel.movetoNext()
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
        /*val questionTextResId=questionBank[currentIndex].textResId*/
        val questionTextResId=quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)

    }

    private fun mostrarPorcentaje(view: View){
       /* var porcentaje: Float=(aciertos.toFloat()/questionBank.size.toFloat())*100
        
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        Snackbar.make(view,
            "Tu porcentaje de aciertos es: "+df.format(porcentaje)+"%",
            Snackbar.LENGTH_SHORT).show()
*/
    }

    private fun checkAnswer(userAnswer: Boolean,view:View){
        /*val correctAnswer=questionBank[currentIndex].answer*/
        val correctAnswer=quizViewModel.currentQuestionAnswer

        /*val messageResId=if(userAnswer==correctAnswer){
            aciertos++
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }*/
        val messageResId= when {
            quizViewModel.isCheater -> R.string.judgment_toast
            userAnswer==correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }
        Snackbar.make(view,
            messageResId,
            Snackbar.LENGTH_SHORT).show()
    }

    private fun checkBotonesRespuesta()
    {
        binding.trueButton.isEnabled=!binding.trueButton.isEnabled
        binding.falseButton.isEnabled=!binding.falseButton.isEnabled
    }
}