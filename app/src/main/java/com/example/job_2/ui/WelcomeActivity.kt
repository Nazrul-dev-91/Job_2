package com.example.job_2.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.databinding.DataBindingUtil
import com.example.job_2.R
import com.example.job_2.databinding.ActivityWelcomeBinding

class WelcomeActivity : ComponentActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome)

        binding.btnGetStarted.setOnClickListener {
            val intent = Intent(this, ProfileListActivity::class.java)
            startActivity(intent)
        }
    }
}
