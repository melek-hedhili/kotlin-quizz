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

class LoginActivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val btnLogin = findViewById(R.id.btnLogin) as Button
        val btnSignUp = findViewById(R.id.btnSignUp) as TextView
        firebaseAuth=FirebaseAuth.getInstance()
        btnLogin.setOnClickListener{
            login()
        }
        btnSignUp.setOnClickListener {
            val intent= Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun login(){
        val email = findViewById(R.id.etEmailAddress) as EditText
        val password = findViewById(R.id.etPassword) as EditText
        if(email.text.toString().isBlank()||password.text.toString().isBlank()){
            Toast.makeText(this,"Email and password cant be blank",Toast.LENGTH_SHORT).show()
            return
        }
        firebaseAuth.signInWithEmailAndPassword(email.text.toString(),password.text.toString()).addOnCompleteListener(this){
            if(it.isSuccessful){
                Toast.makeText(this,"Successfully logged in", Toast.LENGTH_SHORT).show()
                val intent= Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this,"Error Login",Toast.LENGTH_SHORT).show()
            }
        }
    }
}