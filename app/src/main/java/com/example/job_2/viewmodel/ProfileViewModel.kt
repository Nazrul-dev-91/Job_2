package com.example.job_2.viewmodel

import androidx.lifecycle.*
import com.example.job_2.data.UserProfile
import com.example.job_2.data.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {

    /**
     * LiveData holding the list of all user profiles.
     */
    val allProfiles: LiveData<List<UserProfile>> = repository.allUsers.asLiveData()

    /**
     * LiveData representing the total count of profiles.
     */
    val profileCount: LiveData<Int> = allProfiles.map { it.size }

    /**
     * Adds a new profile to the database.
     */
    fun addProfile(profile: UserProfile) = viewModelScope.launch {
        repository.insert(profile)
    }

    /**
     * Updates an existing profile in the database.
     */
    fun updateProfile(profile: UserProfile) = viewModelScope.launch {
        repository.update(profile)
    }

    /**
     * Deletes a profile from the database.
     */
    fun deleteProfile(profile: UserProfile) = viewModelScope.launch {
        repository.delete(profile)
    }

    /**
     * Retrieves a specific profile by its ID.
     */
    suspend fun getProfileById(id: Int): UserProfile? {
        return repository.getById(id)
    }
}

/**
 * Factory class for creating ProfileViewModel with UserRepository dependency.
 */
class ProfileViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
