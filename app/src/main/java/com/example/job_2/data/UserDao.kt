package com.example.job_2.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userProfile: UserProfile)

    @Update
    suspend fun update(userProfile: UserProfile)

    @Delete
    suspend fun delete(userProfile: UserProfile)

    @Query("SELECT * FROM user_profiles ORDER BY id ASC")
    fun getAll(): Flow<List<UserProfile>>

    @Query("SELECT * FROM user_profiles WHERE id = :id")
    suspend fun getById(id: Int): UserProfile?
}
