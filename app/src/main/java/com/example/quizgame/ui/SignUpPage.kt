package com.example.quizgame.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.quizgame.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up_page.*

class SignUpPage : AppCompatActivity() {

    val firebaseAuth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_page)

        supportActionBar?.setTitle("Sign Up")


        signUpButton.setOnClickListener {

            val email = signUpEmailAddress.text.toString()
            val password = signUpPassword.text.toString()

            startAuthentication(email, password)
        }
    }

    fun startAuthentication(email: String, password: String) {



        progressBar.visibility = View.VISIBLE
        signUpButton.isClickable = false
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {

            if (it.isSuccessful) {
                Toast.makeText(applicationContext, "Account created successfully", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE
                signUpButton.isClickable = true;
                finish()
            } else {
                Toast.makeText(applicationContext, it.exception?.localizedMessage?.toString(), Toast.LENGTH_SHORT).show()
                finish()
            }

        }

    }
}