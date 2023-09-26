package com.example.userapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
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
                R.id.navigation_video_lectures-> Toast.makeText(applicationContext, "clicked video lectures", Toast.LENGTH_SHORT).show()
                 R.id.navigation_themes-> Toast.makeText(applicationContext, "clicked Themes", Toast.LENGTH_SHORT).show()
                 R.id.navigation_ebooks-> Toast.makeText(applicationContext, "clicked video lectures", Toast.LENGTH_SHORT).show()
                 R.id.navigation_themes-> Toast.makeText(applicationContext, "clicked video lectures", Toast.LENGTH_SHORT).show()
                 R.id.navigation_website-> Toast.makeText(applicationContext, "clicked video lectures", Toast.LENGTH_SHORT).show()
                 R.id.navigation_share-> Toast.makeText(applicationContext, "clicked video lectures", Toast.LENGTH_SHORT).show()
                 R.id.navigation_rate_us-> Toast.makeText(applicationContext, "clicked video lectures", Toast.LENGTH_SHORT).show()

            }
            true
        }

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        NavController = Navigation.findNavController(this,R.id.frameLayout)
        NavigationUI.setupWithNavController(bottomNavigationView, NavController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true
        }
            return super.onOptionsItemSelected(item)
    }
}