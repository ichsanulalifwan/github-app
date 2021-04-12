package com.dicoding.picodiploma.githubapp.db.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.picodiploma.githubapp.db.AppDatabase
import com.dicoding.picodiploma.githubapp.db.FavUserDao
import com.dicoding.picodiploma.githubapp.db.entity.FavUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FavUserRepository(application: Application) {

    private val favUserDao: FavUserDao
    private var favUserList: LiveData<List<FavUser>>?

    init {
        val db = AppDatabase.getDatabase(application.applicationContext)
        favUserDao = db!!.favDao()
        favUserList = favUserDao.loadAllFav()
    }

    fun getFavuserList():LiveData<List<FavUser>>? {
        return favUserList
    }

    suspend fun getFavUser(username: String): FavUser {
        return favUserDao.loadSingle(username)
    }

    fun insert(user: FavUser) = runBlocking {
        this.launch(Dispatchers.IO) {
            favUserDao.insertUser(user)
        }
    }

    fun delete(user: FavUser) = runBlocking {
        this.launch(Dispatchers.IO) {
            favUserDao.deleteUser(user)
        }
    }
}