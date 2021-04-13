package com.dicoding.picodiploma.githubapp.db

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.picodiploma.githubapp.db.entity.FavUser

@Dao
interface FavUserDao {
    @Query("SELECT * FROM favorite_user_table")
    fun loadAllFav(): LiveData<List<FavUser>>

    @Query("SELECT * FROM favorite_user_table WHERE username = :username ")
    suspend fun loadSingle(username: String): FavUser

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: FavUser)

    @Delete
    suspend fun deleteUser(user: FavUser)

    @Query("SELECT * FROM favorite_user_table")
    fun cursorGetData(): Cursor
}