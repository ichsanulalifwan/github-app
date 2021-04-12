package com.dicoding.picodiploma.githubapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.picodiploma.githubapp.db.entity.FavUser
import com.dicoding.picodiploma.githubapp.db.repository.FavUserRepository

class FavUserViewModel(application: Application) : AndroidViewModel(application) {

    private val FavUserRepo = FavUserRepository(application)
    private val FavUserList : LiveData<List<FavUser>>? = FavUserRepo.getFavuserList()
    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun getTodo(): LiveData<List<FavUser>>? {
        return FavUserList
    }

    fun addFavUser(user: FavUser) {
        status.value = try{
            FavUserRepo.insert(user)
            true
        } catch (e: IllegalArgumentException){
            false
        }
    }

    fun deleteFavUser(user: FavUser) {
        FavUserRepo.delete(user)
    }
}