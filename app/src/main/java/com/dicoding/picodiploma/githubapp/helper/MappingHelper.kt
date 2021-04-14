package com.dicoding.picodiploma.githubapp.helper

import android.database.Cursor
import com.dicoding.picodiploma.githubapp.db.entity.FavUser

object MappingHelper {

    fun mapCursorToArrayList(usersCursor: Cursor?): ArrayList<FavUser> {
        val userLIst = ArrayList<FavUser>()

        usersCursor?.apply {
            while (moveToNext()) {
                val username = getString(getColumnIndexOrThrow("username"))
                val name = getString(getColumnIndexOrThrow("name"))
                val location = getString(getColumnIndexOrThrow("location"))
                val avatar = getString(getColumnIndexOrThrow("avatar"))
                userLIst.add(FavUser(username, name, location, avatar))
            }
        }
        return userLIst
    }
}