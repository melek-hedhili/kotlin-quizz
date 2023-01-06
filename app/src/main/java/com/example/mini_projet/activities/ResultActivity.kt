package com.example.mini_projet.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.mini_projet.R
import com.example.mini_projet.models.Quizz
import com.google.android.material.appbar.MaterialToolbar
import com.google.gson.Gson

class ResultActivity : AppCompatActivity() {
    lateinit var quizz: Quizz
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        setUpViews()
    }



    private fun setUpViews() {
        val quizData = intent.getStringExtra("QUIZZ")
        if (quizData != null) {
            Log.d("quiz data",quizData)
            quizz = Gson().fromJson<Quizz>(quizData, Quizz::class.java)
            calculateScore()
            setAnswerView()
        }

    }

    private fun setAnswerView() {
        val txtAnswer = findViewById<TextView>(R.id.txtAnswer)
        val builder = StringBuilder("")
        for (entry in quizz.questions.entries) {
            val question = entry.value
            builder.append("<font color'#18206F'><b>Question: ${question.description}</b></font><br/><br/>")
            builder.append("<font color='#009688'>Answer: ${question.answer}</font><br/><br/>")
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtAnswer.text = Html.fromHtml(builder.toString(), Html.FROM_HTML_MODE_COMPACT);
        } else {
            txtAnswer.text = Html.fromHtml(builder.toString());
        }
    }

    private fun calculateScore() {
        val txtScore = findViewById<TextView>(R.id.txtScore)
        var score = 0
        for (entry in quizz.questions.entries) {
            val question = entry.value
            if (question.answer == question.userAnswer) {
                score += 10
            }
        }
        txtScore.text = "Your Score : $score"
    }
}