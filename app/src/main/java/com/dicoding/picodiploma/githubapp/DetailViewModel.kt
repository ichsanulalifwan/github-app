package com.dicoding.picodiploma.githubapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class DetailViewModel : ViewModel() {

    private val detailUser = MutableLiveData<User>()

    fun setUserDetail(username: String) {

        val url = "https://api.github.com/users/$username"
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
                try {
                    val responseObject = JSONObject(result)
                    val user = User()
                    user.username = responseObject.getString("login")
                    user.avatar = responseObject.getString("avatar_url")
                    user.name = responseObject.getString("name")
                    user.company = responseObject.getString("company")
                    user.location = responseObject.getString("location")
                    user.repository = responseObject.getString("public_repos")
                    user.followers = responseObject.getString("followers")
                    user.following = responseObject.getString("following")
                    detailUser.postValue(user)
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

    fun getUserDetail(): LiveData<User> {
        return detailUser
    }
}