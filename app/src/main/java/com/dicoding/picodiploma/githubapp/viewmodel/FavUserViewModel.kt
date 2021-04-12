package com.dicoding.picodiploma.githubapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.picodiploma.githubapp.db.entity.FavUser
import com.dicoding.picodiploma.githubapp.db.repository.FavUserRepository
import com.dicoding.picodiploma.githubapp.model.User
import kotlinx.coroutines.runBlocking

class FavUserViewModel(application: Application) : AndroidViewModel(application) {

    private val favUserRepo = FavUserRepository(application)
    private val favUserList: LiveData<List<FavUser>>? = favUserRepo.getFavuserList()
    private val status = MutableLiveData<Boolean>()
    private val currentUser = MutableLiveData<FavUser>()

    val observableCurrentUser: LiveData<FavUser>
        get() = currentUser

    val observableStatus: LiveData<Boolean>
        get() = status

    fun getFavUserList(): LiveData<List<FavUser>>? {
        return favUserList
    }

    fun getFavUser(username: String) = runBlocking {
        currentUser.value = favUserRepo.getFavUser(username)
    }

    fun addFavUser(user: User) {
        status.value = try{
            favUserRepo.insert(user)
            true
        } catch (e: IllegalArgumentException){
            false
        }
    }

    fun deleteFavUser(user: User) {
        favUserRepo.delete(user)
    }
}