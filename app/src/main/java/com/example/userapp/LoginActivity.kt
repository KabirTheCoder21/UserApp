package com.example.userapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.mahfa.dnswitch.DayNightSwitch
import com.mahfa.dnswitch.DayNightSwitchListener

class LoginActivity : AppCompatActivity() {
    private lateinit var email:EditText
    private lateinit var pass:EditText
    private lateinit var btn:Button
    private lateinit var sign:TextView
    private lateinit var firebaseAuth:FirebaseAuth

    private lateinit var dayLand: ImageView
    private lateinit var nightLand: ImageView
    private lateinit var daySky: View
    private lateinit var nightSky: View
    private lateinit var dayNightSwitch: DayNightSwitch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        dayLand = findViewById(R.id.day_landscape)
        nightLand = findViewById(R.id.night_landscape)
        daySky = findViewById(R.id.day_bg)
        nightSky = findViewById(R.id.night_bg)
        dayNightSwitch = findViewById(R.id.day_night_swithch)

        email = findViewById(R.id.email_et)
        pass = findViewById(R.id.pass_et)
        btn = findViewById(R.id.signin)
        sign = findViewById(R.id.signTXT)
        firebaseAuth = FirebaseAuth.getInstance()

        dayNightSwitch.setListener(object : DayNightSwitchListener {
            override fun onSwitch(isNight: Boolean) {
                if (isNight) {
                    dayLand.animate().alpha(0f).setDuration(1300)
                    daySky.animate().alpha(0f).setDuration(1300)
                } else {
                    dayLand.animate().alpha(1f).setDuration(1300)
                    daySky.animate().alpha(1f).setDuration(1300)
                }
            }
        })

        btn.setOnClickListener {

//            binding.passwordlogin.clearFocus()

            val mail = email.text.toString()
            val pass = pass.text.toString()
//                val confirmPass=binding.
            if(mail.isNotEmpty() && pass.isNotEmpty() ){

                firebaseAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener {
                    if (it.isSuccessful){

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Toast.makeText(this,it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else{
                Toast.makeText(this,"Enter All fields", Toast.LENGTH_SHORT).show()
                Toast.makeText(this,"pura daalo", Toast.LENGTH_SHORT).show()
            }

        }
        sign.setOnClickListener {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
            finish()
        }
    }
    override fun onBackPressed() {
        finish()
    }
}