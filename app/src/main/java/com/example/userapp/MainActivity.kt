package com.example.userapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
private lateinit var bottomNavigationView: BottomNavigationView
private lateinit var NavController : NavController
private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
private lateinit var drawerLayout: DrawerLayout
private lateinit var navigationView: NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_drawer)

        actionBarDrawerToggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navigationView.setNavigationItemSelectedListener {
            when(it.itemId)
            {
                 R.id.navigation_video_lectures-> Toast.makeText(applicationContext, "we will upload soon! Keep in touch with us", Toast.LENGTH_SHORT).show()
                 R.id.navigation_logout-> Toast.makeText(applicationContext, "clicked logout", Toast.LENGTH_SHORT).show()
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
}