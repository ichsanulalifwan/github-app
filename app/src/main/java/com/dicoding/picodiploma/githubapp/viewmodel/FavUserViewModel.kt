package com.dicoding.picodiploma.githubapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.picodiploma.githubapp.db.entity.FavUser
import com.dicoding.picodiploma.githubapp.db.repository.FavUserRepository
import com.dicoding.picodiploma.githubapp.model.User

class FavUserViewModel(application: Application) : AndroidViewModel(application) {

    private val favUserRepo = FavUserRepository(application)
    private val favUserList: LiveData<List<FavUser>>? = favUserRepo.getFavuserList()

    fun getFavUserList(): LiveData<List<FavUser>>? {
        return favUserList
    }

    fun getFavUser(username: String) = favUserRepo.getFavUser(username)

    fun addFavUser(user: User) = favUserRepo.insert(user)

    fun deleteFavUser(user: User) = favUserRepo.delete(user)
}