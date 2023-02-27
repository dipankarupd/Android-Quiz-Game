package com.example.quizgame.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.quizgame.R
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.sign_out, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {

            R.id.logout -> {

                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this@MainActivity , LoginPage :: class.java)
                startActivity(intent)
                Toast.makeText(applicationContext, "Successfully logged out", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
        return true
    }
}