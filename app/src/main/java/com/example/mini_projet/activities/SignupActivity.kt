package com.example.mini_projet.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.mini_projet.R
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {
    lateinit var firebaseAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        val btnSignUp = findViewById(R.id.btnSignUp) as Button
        val btnLogin = findViewById(R.id.btnLogin) as TextView
        firebaseAuth=FirebaseAuth.getInstance()
        btnSignUp.setOnClickListener{
            signUpUser()
        }
        btnLogin.setOnClickListener {
            val intent= Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun signUpUser(){

        val email = findViewById(R.id.etEmailAddress) as EditText
        val password = findViewById(R.id.etPassword) as EditText
        val confirm_password = findViewById(R.id.etConfirmPassword) as EditText
        if(email.text.toString().isBlank()||password.text.toString().isBlank()||confirm_password.text.toString().isBlank()){
            Toast.makeText(this,"Email and password cant be blank",Toast.LENGTH_SHORT).show()
            return
        }
        if(password.text.toString()!=confirm_password.text.toString()){
            Toast.makeText(this,"Password and confirm password are not the same",Toast.LENGTH_SHORT).show()
            return
        }
        firebaseAuth.createUserWithEmailAndPassword(email.text.toString(),password.text.toString())
            .addOnCompleteListener(this){
            if(it.isSuccessful){
                Toast.makeText(this,"Successfully created user",Toast.LENGTH_SHORT).show()
                val intent= Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this,"Error creating user",Toast.LENGTH_SHORT).show()
            }
        }

    }
}