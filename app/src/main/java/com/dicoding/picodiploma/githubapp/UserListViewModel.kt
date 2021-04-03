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

class UserListViewModel : ViewModel() {

    private val listUsers = MutableLiveData<ArrayList<User>>()

    fun setUserList(username: String) {

        val listUser = ArrayList<User>()
        val url = "https://api.github.com/search/users?q=$username"
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
                    val responseObject = JSONObject(result)
                    val items = responseObject.getJSONArray("items")
                    for (i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        val user = User()
                        user.username  = item.getString("login")
                        user.avatar= item.getString("avatar_url")
                        listUser.add(user)
                    }
                    listUsers.postValue(listUser)
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

    fun getUserList(): LiveData<ArrayList<User>> {
        return listUsers
    }
}