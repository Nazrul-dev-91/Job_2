package com.example.job_2.data

import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {

    val allUsers: Flow<List<UserProfile>> = userDao.getAll()

    suspend fun insert(userProfile: UserProfile) {
        userDao.insert(userProfile)
    }

    suspend fun update(userProfile: UserProfile) {
        userDao.update(userProfile)
    }

    suspend fun delete(userProfile: UserProfile) {
        userDao.delete(userProfile)
    }

    suspend fun getById(id: Int): UserProfile? {
        return userDao.getById(id)
    }
}
