package com.example.quizgame.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import com.example.quizgame.R
import kotlinx.android.synthetic.main.activity_welcome_screen.*

class WelcomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_screen)

        val animation = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.start_anim
        )
        textViewDot.startAnimation(animation)


        val handler = Handler(Looper.getMainLooper())

        handler.postDelayed(
            Runnable {
                val intent = Intent(this@WelcomeScreen, MainActivity :: class.java)
                startActivity(intent)
                finish()
            },
            5000
        )
    }
}