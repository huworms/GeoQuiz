package com.bignerdranch.android.geoquiz

import androidx.lifecycle.SavedStateHandle
import org.junit.Assert.*
import org.junit.Test

class QuizViewModelTest
@Test
fun providesExpectedQuestionText(){
    val savedStateHandle=SavedStateHandle()
    val quizViewModel= QuizViewModel(savedStateHandle)
    assertEquals(R.string.question_australia, quizViewModel.currentQuestionText)

}