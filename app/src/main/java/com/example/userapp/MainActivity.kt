package com.example.userapp

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
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
//                 R.id.navigation_logout-> Toast.makeText(applicationContext, "clicked logout", Toast.LENGTH_SHORT).show()
                 R.id.navigation_logout-> {
                     showAlertDialog()

                 }
                 R.id.navigation_ebooks-> Toast.makeText(applicationContext, "clicked on ebooks", Toast.LENGTH_SHORT).show()
                 R.id.navigation_website-> Toast.makeText(applicationContext, "clicked on websites", Toast.LENGTH_SHORT).show()
                 R.id.navigation_share-> Toast.makeText(applicationContext, "clicked share", Toast.LENGTH_SHORT).show()
                 R.id.navigation_rate_us-> Toast.makeText(applicationContext, "clicked rate us", Toast.LENGTH_SHORT).show()
                R.id.feedback -> {
                    Toast.makeText(applicationContext, "Clicked Feedback", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        NavController = Navigation.findNavController(this,R.id.frameLayout)
        NavigationUI.setupWithNavController(bottomNavigationView, NavController)
    }

    private fun sendEmail() {
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
        val alertDialogBuilder = AlertDialog.Builder(this)

        // Set the dialog title
        alertDialogBuilder.setTitle("Warning !")

        // Set the dialog message
        alertDialogBuilder.setMessage("Do you want to Logout ?")

        // Add a positive button
        alertDialogBuilder.setPositiveButton("Yes") { dialog, which ->
            // Positive button action
            // You can put your code here to handle the positive button click
            val mAuth = FirebaseAuth.getInstance()
            mAuth.signOut()
            progressDialog.dismiss()
            val intent = Intent(this, SignIn::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        // Add a negative button
        alertDialogBuilder.setNegativeButton("No") { dialog, which ->
            // Negative button action
            // You can put your code here to handle the negative button click

        }

        // Create and show the alert dialog
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}