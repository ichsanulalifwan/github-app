package com.dicoding.picodiploma.githubapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.githubapp.model.User
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import java.lang.Exception

class FollowingViewModel : ViewModel() {


    private val listFolloing = MutableLiveData<ArrayList<User>>()

    fun setFollowingList(username: String) {

        val listUser = ArrayList<User>()
        val url = "https://api.github.com/users/$username/following"
        val client = AsyncHttpClient()
        client.addHeader("Authorization","token 74a052e7810f2fd367b034ffbfc32d8992a8c656")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                try{
                    val response = JSONArray(result)
                    for (i in 0 until response.length()) {
                        val data = response.getJSONObject(i)
                        val user = User()
                        user.username  = data.getString("login")
                        user.avatar= data.getString("avatar_url")
                        listUser.add(user)
                    }
                    listFolloing.postValue(listUser)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d("onFailure", error?.message.toString())
            }
        })
    }

    fun getFollowingList(): LiveData<ArrayList<User>> {
        return listFolloing
    }
}