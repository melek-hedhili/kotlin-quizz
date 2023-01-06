package com.example.mini_projet.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mini_projet.R
import com.example.mini_projet.adapters.OptionAdapter
import com.example.mini_projet.models.Question
import com.example.mini_projet.models.Quizz
import com.google.firebase.firestore.FirebaseFirestore

class QuestionActivity : AppCompatActivity() {
    var quizzes:MutableList<Quizz>?=null
     var questions:MutableMap<String , Question>?=null
    var index=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        setUpFireStore()
        setUpEventListener()
    }

    private fun setUpEventListener() {
        val btnPrevious = findViewById<Button>(R.id.btnPrevious)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        val btnNext = findViewById<Button>(R.id.btnNext)
        btnPrevious.setOnClickListener {
            index--
            bindViews()
        }
        btnNext.setOnClickListener {
            index++
            bindViews()
        }
        btnSubmit.setOnClickListener {
            val intent = Intent(this,ResultActivity::class.java)
            val json =Gson().toJson(quizzes!![0])
            intent.putExtra("QUIZZ",json)
            startActivity(intent)

        }
    }

    private fun setUpFireStore() {
        val firestore = FirebaseFirestore.getInstance()
        val date : String? = intent.getStringExtra("DATE")
        if(date!=null){
            firestore.collection("quizzes").whereEqualTo("title",date).get().addOnSuccessListener {
                if(it!=null && !it.isEmpty){
                    //Log.d("DATA",it.toObjects(Quizz::class.java).toString())
                    quizzes=it.toObjects(Quizz::class.java)
                    questions=quizzes!![0].questions
                    bindViews()
                }

            }
        }
    }

    private fun bindViews() {
        val btnPrevious = findViewById<Button>(R.id.btnPrevious)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        val btnNext = findViewById<Button>(R.id.btnNext)
        val description = findViewById<TextView>(R.id.description)
        val optionList = findViewById<RecyclerView>(R.id.optionList)
        btnPrevious.visibility= View.GONE
        btnSubmit.visibility= View.GONE
        btnNext.visibility= View.GONE
        if(index==1){
            btnNext.visibility= View.VISIBLE
        }else if(index == questions!!.size){
            btnSubmit.visibility= View.VISIBLE
            btnNext.visibility= View.VISIBLE
        }else{
            btnPrevious.visibility= View.VISIBLE
            btnNext.visibility= View.VISIBLE
        }
        val question:Question?=questions!!["question$index"]
        question?.let {
            description.text=it.description
            val optionAdapter=OptionAdapter(this,it)
            optionList.layoutManager=LinearLayoutManager(this)
            optionList.adapter=optionAdapter
            optionList.setHasFixedSize(true)
        }


        /*val questions=Question(
            "How much will the winning team be awarded at this year's World Cup",
            "36M€",
            "12M€",
            "30M€",
            "5M€"
        )*/

    }
}