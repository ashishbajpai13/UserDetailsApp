package com.android.userdat.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.userdat.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.userDetailsBtn.setOnClickListener {
            startActivity(Intent(this, UserDetailsActivity::class.java))
        }
        binding.userKYCDetailsBtn.setOnClickListener {
            startActivity(Intent(this, UserKYCDetailsActivity::class.java))
        }
        binding.userCompleteProfileBtn.setOnClickListener {
            startActivity(Intent(this, UserProfileActivity::class.java))
        }
    }
}