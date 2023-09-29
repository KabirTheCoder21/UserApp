package com.example.userapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:kabirseth81@gmail.com")
                putExtra(Intent.EXTRA_SUBJECT, topic.text.toString())
                putExtra(Intent.EXTRA_TEXT, feedback.text.toString())
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                } else {
                    // Handle the case where no email client is available
                    // You can show a message or use a different approach
                    Toast.makeText(this@Feedback, "No Client Exist", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}