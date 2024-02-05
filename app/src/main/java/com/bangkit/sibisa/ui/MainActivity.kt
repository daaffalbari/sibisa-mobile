package com.bangkit.sibisa.ui

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bangkit.sibisa.R
import com.bangkit.sibisa.databinding.ActivityMainBinding
import com.bangkit.sibisa.pref.UserPreference
import com.bangkit.sibisa.ui.register.RegisterActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        supportActionBar?.let {
            it.setDisplayShowTitleEnabled(false)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                it.setBackgroundDrawable(ColorDrawable(getColor(R.color.riviera_paradise)))
            }

            // logo for action bar
            it.setDisplayShowCustomEnabled(true)
            val view = layoutInflater.inflate(R.layout.custom_image, null)
            it.customView = view
        }

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_leaderboard, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        checkUserStatus()
    }

    private fun checkUserStatus() {
        token = getUserToken()
        if (token.isNullOrEmpty()) {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }

    private fun getUserToken(): String? {
        return UserPreference(this@MainActivity).getToken()
    }
}