package com.dicoding.picodiploma.githubapp.db.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.picodiploma.githubapp.db.AppDatabase
import com.dicoding.picodiploma.githubapp.db.FavUserDao
import com.dicoding.picodiploma.githubapp.db.entity.FavUser
import com.dicoding.picodiploma.githubapp.model.User
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

    fun getFavuserList(): LiveData<List<FavUser>>? {
        return favUserList
    }

    fun getFavUser(username: String): LiveData<List<FavUser>> {
        return favUserDao.loadSingle(username)
    }

    fun insert(user: User) = runBlocking {
        this.launch(Dispatchers.IO) {
            favUserDao.insertUser(
                FavUser(
                    username = user.username,
                    name = user.name,
                    location = user.location,
                    repository = user.repository,
                    company = user.company,
                    followers = user.followers,
                    following = user.following,
                    avatar = user.avatar
                )
            )
        }
    }

    fun delete(user: User) = runBlocking {
        this.launch(Dispatchers.IO) {
            favUserDao.deleteUser(
                FavUser(
                    username = user.username,
                    name = user.name,
                    location = user.location,
                    repository = user.repository,
                    company = user.company,
                    followers = user.followers,
                    following = user.following,
                    avatar = user.avatar
                )
            )
        }
    }
}