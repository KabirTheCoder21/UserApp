package com.example.userapp


import android.app.Dialog

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.PendingIntentCompat.send

import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {
private lateinit var bottomNavigationView: BottomNavigationView
private lateinit var NavController : NavController
private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
private lateinit var drawerLayout: DrawerLayout
private lateinit var navigationView: NavigationView
private lateinit var rateNow : AppCompatButton
private lateinit var rateLater : AppCompatButton
private lateinit var ratingbar : RatingBar
private lateinit var rating_image:ImageView
var rate =0f

private lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_drawer)
        progressDialog =ProgressDialog(this)

        actionBarDrawerToggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navigationView.setNavigationItemSelectedListener {
            when(it.itemId)
            {
                 R.id.navigation_video_lectures-> Toast.makeText(applicationContext, "we will upload soon! Keep in touch with us", Toast.LENGTH_SHORT).show()
                 R.id.navigation_ebooks-> {Toast.makeText(applicationContext, "clicked on ebooks", Toast.LENGTH_SHORT).show()
                 val intent = Intent(this,EbookActivity::class.java)
                 startActivity(intent)
                 }
                 R.id.navigation_website->{ Toast.makeText(applicationContext, "clicked on websites", Toast.LENGTH_SHORT).show()
                     val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.lkouniv.ac.in/"))
                     startActivity(webIntent)
                 }
                R.id.navigation_logout-> {
                     showAlertDialog()
                 }
                 R.id.navigation_share-> Toast.makeText(applicationContext, "clicked share", Toast.LENGTH_SHORT).show()
                 R.id.navigation_rate_us-> {
                     Toast.makeText(applicationContext, "clicked rate us", Toast.LENGTH_SHORT)
                         .show()
                     rate()
                 }
                R.id.feedback -> {
                    Toast.makeText(applicationContext, "Clicked Feedback", Toast.LENGTH_SHORT).show()
                    send()
                }
            }
            true
        }

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        NavController = Navigation.findNavController(this,R.id.frameLayout)
        NavigationUI.setupWithNavController(bottomNavigationView, NavController)
    }

    private fun rate() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.rate_us_dialog)
         rateNow = dialog.findViewById<AppCompatButton>(R.id.rate_now)
         rateLater  = dialog.findViewById(R.id.rate_later)
         ratingbar = dialog.findViewById(R.id.rating_bar)
         rating_image = dialog.findViewById<ImageView>(R.id.rating_image)
          dialog.show()
        ratingbar.setOnRatingBarChangeListener(object  : RatingBar.OnRatingBarChangeListener{
            override fun onRatingChanged(p0: RatingBar?, p1: Float, p2: Boolean) {
                if(p1<=1)
                {
                    rating_image.setImageResource(R.drawable.one_star)
                }
                else if(p1<=2)
                {
                    rating_image.setImageResource(R.drawable.two_star)
                }
                else if(p1<=3)
                {
                    rating_image.setImageResource(R.drawable.three_star)
                }
                else if(p1<=4)
                {
                    rating_image.setImageResource(R.drawable.four_star)
                }
                else if(p1<=5)
                {
                    rating_image.setImageResource(R.drawable.five_star)
                }
                animate(rating_image)
                rate = p1
            }
        })
        rateNow.setOnClickListener {
            dialog.dismiss()
            Toast.makeText(this, "thankyou for rating us" +
                    "!", Toast.LENGTH_SHORT).show()
        }
        rateLater.setOnClickListener {
            dialog.dismiss()
            Toast.makeText(this, "Please sure to be rate us later", Toast.LENGTH_SHORT).show()
        }
    }
    private fun animate(ratingImage: ImageView){
        val scaleAnimation = ScaleAnimation(0f,1f,0f,1f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f)
        scaleAnimation.fillAfter = true
        scaleAnimation.duration = 200
        ratingImage.animation = scaleAnimation
    }

    private fun send() {
        val intent = Intent(this,Feedback::class.java)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true
        }
            return super.onOptionsItemSelected(item)
    }


    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        else{
            super.onBackPressed()
        }
    }
    private fun showAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle("Warning!")
            .setMessage("Do you want to Logout ?")
            .setPositiveButton("Yes") { dialog, which ->
                // Positive button action
                val mAuth = FirebaseAuth.getInstance()
                mAuth.signOut()
                val intent = Intent(this, SignIn::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
            .setNegativeButton("No") { dialog, which ->
                // Negative button action
               dialog.dismiss()
            }
            .show()
    }

}