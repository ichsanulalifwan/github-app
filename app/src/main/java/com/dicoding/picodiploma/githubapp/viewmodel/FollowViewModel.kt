package com.dicoding.picodiploma.githubapp.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.githubapp.BuildConfig
import com.dicoding.picodiploma.githubapp.model.User
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import java.lang.Exception

class FollowViewModel : ViewModel() {

    private lateinit var url: String
    private val listFollow = MutableLiveData<ArrayList<User>>()

    fun setFollowList(context: Context?, username: String, index: Int) {

        when (index) {
            0 -> url = "https://api.github.com/users/$username/followers"
            1 -> url = "https://api.github.com/users/$username/following"
        }

        val listUser = ArrayList<User>()
        val client = AsyncHttpClient()
        client.addHeader("Authorization", API_KEY)
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                try {
                    val response = JSONArray(result)
                    for (i in 0 until response.length()) {
                        val data = response.getJSONObject(i)
                        val user = User()
                        user.username = data.getString("login")
                        user.avatar = data.getString("avatar_url")
                        listUser.add(user)
                    }
                    listFollow.postValue(listUser)
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
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                Log.d("onFailure", error?.message.toString())
            }
        })
    }

    fun getFollowList(): LiveData<ArrayList<User>> {
        return listFollow
    }
    companion object {
        private const val API_KEY = BuildConfig.API_KEY
    }
}