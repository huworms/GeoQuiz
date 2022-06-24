package com.bignerdranch.android.geoquiz

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        scenario= ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun showsFirstQuestionOnLaunch(){
        Espresso.onView(ViewMatchers.withId(R.id.question_text_view))
            .check(ViewAssertions.matches(ViewMatchers.withText(R.string.question_australia)))
    }

    @Test
    fun showsSecondQuestionAfterNextPress(){
        Espresso.onView(ViewMatchers.withId(R.id.next_button)).perform(ViewActions.click())
            Espresso.onView(ViewMatchers.withId(R.id.question_text_view))
                .check(ViewAssertions.matches(ViewMatchers.withText(R.string.question_oceans)))
    }

    @Test
    fun handlesActivityRecreation(){
        Espresso.onView(ViewMatchers.withId(R.id.next_button)).perform(ViewActions.click())
        scenario.recreate()
        Espresso.onView(ViewMatchers.withId(R.id.question_text_view))
            .check(ViewAssertions.matches(ViewMatchers.withText(R.string.question_oceans)))
    }

    @Test
    fun checkCurrentQuestionAnswer(){

    }
}