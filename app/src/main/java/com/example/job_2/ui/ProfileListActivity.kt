package com.example.job_2.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.job_2.R
import com.example.job_2.data.AppDatabase
import com.example.job_2.data.UserProfile
import com.example.job_2.data.UserRepository
import com.example.job_2.databinding.ActivityProfileListBinding
import com.example.job_2.viewmodel.ProfileViewModel
import com.example.job_2.viewmodel.ProfileViewModelFactory

class ProfileListActivity : AppCompatActivity(), ProfileAdapter.OnItemClickListener {

    private lateinit var binding: ActivityProfileListBinding
    private lateinit var viewModel: ProfileViewModel
    private lateinit var adapter: ProfileAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_list)

        val repository = UserRepository(AppDatabase.getDatabase(this).userDao())
        val factory = ProfileViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[ProfileViewModel::class.java]

        setupRecyclerView()

        viewModel.allProfiles.observe(this) { profiles ->
            adapter.submitList(profiles)
        }

        viewModel.profileCount.observe(this) { count ->
            binding.count = count
        }

        binding.fabAddProfile.setOnClickListener {
            val intent = Intent(this, AddProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        adapter = ProfileAdapter(this)
        binding.rvProfiles.layoutManager = LinearLayoutManager(this)
        binding.rvProfiles.adapter = adapter
    }

    override fun onItemClick(profile: UserProfile) {
        val intent = Intent(this, SingleProfileActivity::class.java)
        intent.putExtra("PROFILE_ID", profile.id)
        startActivity(intent)
    }

    override fun onEditClick(profile: UserProfile) {
        val intent = Intent(this, AddProfileActivity::class.java)
        intent.putExtra("PROFILE_ID", profile.id)
        startActivity(intent)
    }

    override fun onDeleteClick(profile: UserProfile) {
        viewModel.deleteProfile(profile)
    }
}