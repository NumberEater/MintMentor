package com.numbereater.investmentapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var activeFragment: BottomNavigationFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation = findViewById(R.id.bottom_navigation)

        loadRelevantFragment()

        configureNavigation()

        setRelevantBottomNavItem()
    }

    private fun loadRelevantFragment() {
        if (intent.getBooleanExtra(Constants.LESSON_COMPLETE_INTENT_TAG, false)) {
            setActiveFragment(LearnFragment())
        } else {
            setActiveFragment(HomeFragment())
        }
    }

    private fun setActiveFragment(fragment: BottomNavigationFragment) {
        val transaction = supportFragmentManager.beginTransaction()
        activeFragment = fragment
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    private fun setRelevantBottomNavItem() {
        setSelectedBottomNavItem(activeFragment.bottomNavigationButtonId)
    }

    private fun setSelectedBottomNavItem(id: Int) {
        bottomNavigation.selectedItemId = id
    }

    private fun configureNavigation() {
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_action -> {
                    setActiveFragment(HomeFragment())
                    true
                }
                R.id.learn_action -> {
                    setActiveFragment(LearnFragment())
                    true
                }
                R.id.about_action -> {
                    setActiveFragment(AboutFragment())
                    true
                }
                else -> false
            }
        }
    }
}
