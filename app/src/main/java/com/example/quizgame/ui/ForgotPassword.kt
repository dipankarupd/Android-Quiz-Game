package com.example.quizgame.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.quizgame.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_forgot_password.*


class ForgotPassword : DialogFragment() {

    val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        sendMailButton.setOnClickListener {

            val resetPasswordEmail = pwMessageEmail.text.toString()
            auth.sendPasswordResetEmail(resetPasswordEmail)
            Toast.makeText(context, "Reset email sent successfully", Toast.LENGTH_SHORT).show()
            dialog!!.dismiss()
        }
    }

}