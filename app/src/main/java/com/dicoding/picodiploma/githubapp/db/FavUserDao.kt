package com.dicoding.picodiploma.githubapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.picodiploma.githubapp.db.entity.FavUser

@Dao
interface FavUserDao {
    @Query("SELECT * FROM favorite_user")
    fun loadAllFav(): LiveData<List<FavUser>>

    @Query("SELECT * FROM favorite_user WHERE username = :username ")
    fun loadSingle(username: String): LiveData<List<FavUser>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: FavUser)

    @Delete
    suspend fun deleteUser(user: FavUser)
}