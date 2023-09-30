package com.example.userapp

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class Feedback : AppCompatActivity() {
    private lateinit var topic : EditText
    private lateinit var feedback : EditText
    private lateinit var send : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)
topic = findViewById(R.id.topic)
        feedback = findViewById(R.id.feedback)
        send = findViewById(R.id.send)
        send.setOnClickListener {
            val subject = topic.text.toString().trim()
            val txt = feedback.text.toString().trim()
            if (!subject.isEmpty()
                && !txt.isEmpty()) {
                sendEmail(subject,txt)
            } else {
                Toast.makeText(this, "Please fill all the fields",
                    Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("IntentReset")
    private fun sendEmail(subject: String, txt: String) {
        val mIntent = Intent(Intent.ACTION_SENDTO)
        mIntent.data = Uri.parse("mailto:kabirseth81@gmail.com")
        mIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        mIntent.putExtra(Intent.EXTRA_TEXT, txt)

        try {
            startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }
}