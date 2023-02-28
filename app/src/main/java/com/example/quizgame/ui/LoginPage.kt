package com.example.quizgame.ui

import android.content.ContentProviderClient
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentManager
import com.example.quizgame.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Api
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login_page.*


class LoginPage : AppCompatActivity() {

    var auth : FirebaseAuth = FirebaseAuth.getInstance()

    lateinit var googleSignInClient: GoogleSignInClient

    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        // register for activity result launcher:

        registerForGoogleActivity()

        signInButton.setOnClickListener {

            var emailAddress = signInEmailText.text.toString()
            var password = signInPasswordText.text.toString()

            signIn(emailAddress, password)
        }

        signInWithGoogleButton.setOnClickListener {

            signInGoogle()
        }

        signUpTxt.setOnClickListener {

            val intent = Intent(this@LoginPage, SignUpPage :: class.java)
            startActivity(intent)
        }

        forgotPwText.setOnClickListener {

                passwordReset()

        }
    }

    private fun registerForGoogleActivity() {

        activityResultLauncher = registerForActivityResult (
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback { result ->
                val resultCode = result.resultCode
                val data = result.data

                if (resultCode == RESULT_OK && data != null) {
                    // thread creation:
                    val task : Task <GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
                    firebaseSignInWithGoogle(task)
                }
            }
        )
    }

    private fun firebaseSignInWithGoogle(task: Task<GoogleSignInAccount>) {

        try {
            val account : GoogleSignInAccount = task.getResult(ApiException :: class.java)
            Toast.makeText(applicationContext, "Successfully created account", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, MainActivity :: class.java)
            startActivity(intent)
            finish()
            updateUi(account)
        } catch (e : ApiException) {
            Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()

        }
    }

    private fun updateUi(account: GoogleSignInAccount) {

        val authCredential = GoogleAuthProvider.getCredential(account.idToken, null)

        auth.signInWithCredential(authCredential).addOnCompleteListener { it ->

            if (it.isSuccessful) {

//                val intent = Intent(this,MainActivity::class.java)
//                startActivity(intent)
//                finish()
            } else {
                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun signInGoogle() {
        val gso = GoogleSignInOptions.Builder (GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("772457678021-0s4ogqb5fcbmrn2is74me58vuo2s4rjb.apps.googleusercontent.com")
            .requestEmail()
            .build()


        googleSignInClient = GoogleSignIn.getClient(this,gso)
        signInWithGoogle()
    }

    private fun signInWithGoogle() {

        val intent : Intent = googleSignInClient.signInIntent
        activityResultLauncher.launch(intent)

    }

    private fun signIn(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val intent = Intent(this@LoginPage, MainActivity :: class.java)
                    startActivity(intent)

                } else {
                    Toast.makeText(this@LoginPage, task?.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }
    }

    // sign in until signed out:

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if(currentUser != null){

            val intent = Intent(this@LoginPage, MainActivity :: class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun passwordReset() {

        var fragmentManager : FragmentManager = supportFragmentManager
        var forgotPwFragment = ForgotPassword()
        forgotPwFragment.show(fragmentManager , "ForgotPwFragment")
    }


}