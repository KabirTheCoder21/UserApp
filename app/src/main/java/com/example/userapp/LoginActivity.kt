package com.example.userapp

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
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

    private lateinit var daySky: View
    private lateinit var nightSky: View
    private lateinit var dayNightSwitch: DayNightSwitch
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        daySky = findViewById(R.id.day_bg)
        nightSky = findViewById(R.id.night_bg)
        dayNightSwitch = findViewById(R.id.day_night_swithch)

        email = findViewById(R.id.email_et)
        pass = findViewById(R.id.pass_et)
        btn = findViewById(R.id.signin)
        sign = findViewById(R.id.signTXT)
        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog =ProgressDialog(this)

        dayNightSwitch.setListener(object : DayNightSwitchListener {
            override fun onSwitch(isNight: Boolean) {
                if (isNight) {
//                    dayLand.animate().alpha(0f).setDuration(1300)
                    daySky.animate().alpha(0f).setDuration(1300)
                } else {
//                    dayLand.animate().alpha(1f).setDuration(1300)
                    daySky.animate().alpha(1f).setDuration(1300)
                }
            }
        })

        btn.setOnClickListener {
            progressDialog.setMessage("Loading !")


//            binding.passwordlogin.clearFocus()

            val mail = email.text.toString()
            val pass = pass.text.toString()
//                val confirmPass=binding.
            if(mail.isNotEmpty() && pass.isNotEmpty() ){
                if(isInternetAvailable()){
                progressDialog.show()
                firebaseAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener {
                    if (it.isSuccessful){
                        progressDialog.dismiss()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        progressDialog.dismiss()
                        Toast.makeText(this,it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }}
                else{
                    Toast.makeText(this, "Turn on Connectivity", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                progressDialog.dismiss()
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

    private fun isInternetAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork

        if (network != null) {
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
            return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        }
        return false
    }

    override fun onBackPressed() {
        finish()
    }
}