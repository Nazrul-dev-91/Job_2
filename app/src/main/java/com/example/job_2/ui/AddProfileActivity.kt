package com.example.job_2.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.job_2.R
import com.example.job_2.data.AppDatabase
import com.example.job_2.data.UserProfile
import com.example.job_2.data.UserRepository
import com.example.job_2.databinding.ActivityAddProfileBinding
import com.example.job_2.viewmodel.ProfileViewModel
import com.example.job_2.viewmodel.ProfileViewModelFactory
import kotlinx.coroutines.launch

class AddProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddProfileBinding
    private lateinit var viewModel: ProfileViewModel
    private var profileId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_profile)

        val repository = UserRepository(AppDatabase.getDatabase(this).userDao())
        val factory = ProfileViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[ProfileViewModel::class.java]

        profileId = intent.getIntExtra("PROFILE_ID", -1)

        if (profileId != -1) {
            binding.title = getString(R.string.edit_profile)
            loadProfileData(profileId)
        } else {
            binding.title = getString(R.string.add_new_profile)
        }

        binding.btnSave.setOnClickListener {
            saveProfile()
        }
    }

    private fun loadProfileData(id: Int) {
        lifecycleScope.launch {
            val profile = viewModel.getProfileById(id)
            profile?.let {
                binding.etName.setText(it.name)
                binding.etEmail.setText(it.email)
                binding.etPhone.setText(it.phone)
                binding.etAddress.setText(it.address)
                binding.etDesignation.setText(it.designation)
            }
        }
    }

    private fun saveProfile() {
        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()
        val address = binding.etAddress.text.toString().trim()
        val designation = binding.etDesignation.text.toString().trim()

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || designation.isEmpty()) {
            Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show()
            return
        }

        val profile = UserProfile(
            id = if (profileId != -1) profileId else 0,
            name = name,
            email = email,
            phone = phone,
            address = address,
            designation = designation
        )

        if (profileId != -1) {
            viewModel.updateProfile(profile)
            Toast.makeText(this, getString(R.string.profile_updated), Toast.LENGTH_SHORT).show()
        } else {
            viewModel.addProfile(profile)
            Toast.makeText(this, getString(R.string.profile_added), Toast.LENGTH_SHORT).show()
        }

        finish()
    }
}