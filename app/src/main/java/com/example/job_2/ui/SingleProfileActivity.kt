package com.example.job_2.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.job_2.R
import com.example.job_2.data.AppDatabase
import com.example.job_2.data.UserRepository
import com.example.job_2.databinding.ActivitySingleProfileBinding
import com.example.job_2.viewmodel.ProfileViewModel
import com.example.job_2.viewmodel.ProfileViewModelFactory
import kotlinx.coroutines.launch

class SingleProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingleProfileBinding
    private lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_single_profile)

        val repository = UserRepository(AppDatabase.getDatabase(this).userDao())
        val factory = ProfileViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[ProfileViewModel::class.java]

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Profile Details"

        val profileId = intent.getIntExtra("PROFILE_ID", -1)
        if (profileId != -1) {
            fetchProfile(profileId)
        } else {
            Toast.makeText(this, "Profile not found", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun fetchProfile(id: Int) {
        lifecycleScope.launch {
            val profile = viewModel.getProfileById(id)
            if (profile != null) {
                binding.profile = profile
            } else {
                Toast.makeText(this@SingleProfileActivity, "Profile not found", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
