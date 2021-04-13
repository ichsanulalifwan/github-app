package com.dicoding.picodiploma.consumerapp.helper

import android.database.Cursor
import com.dicoding.picodiploma.consumerapp.model.User

object MappingHelper {

    fun mapCursorToArrayList(usersCursor: Cursor?): ArrayList<User> {
        val userLIst = ArrayList<User>()

        usersCursor?.apply {
            while (moveToNext()) {
                val username = getString(getColumnIndexOrThrow("username"))
                val name = getString(getColumnIndexOrThrow("name"))
                val location = getString(getColumnIndexOrThrow("location"))
                val avatar = getString(getColumnIndexOrThrow("avatar"))
                userLIst.add(User(username, name, location, avatar))
            }
        }
        return userLIst
    }
}