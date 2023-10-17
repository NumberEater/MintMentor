package com.numbereater.investmentapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private fun configureNavigation() {
        val navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_action -> {
                    // Load home fragment
                    loadFragment(HomeFragment())
                    true
                }
                R.id.learn_action -> {
                    // Load about fragment
                    loadFragment(LearnFragment())
                    true
                }
                R.id.about_action -> {
                    // Load account fragment
                    loadFragment(AboutFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Load correct fragment when main activity starts
        if (intent.extras?.getBoolean("lesson-complete") == true) {
            loadFragment(LearnFragment())
            val bottomNavigationBar = findViewById<BottomNavigationView>(R.id.bottom_navigation)
            bottomNavigationBar.selectedItemId = R.id.learn_action
        } else {
            loadFragment(HomeFragment())
        }


        // Configure the bottom navigation bar
        configureNavigation()
    }
}
