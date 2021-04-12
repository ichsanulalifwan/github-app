package com.dicoding.picodiploma.githubapp.db

import android.provider.BaseColumns

internal class DatabaseContract {

    internal class UserColoumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favorite_user"
            const val USERNAME = "username"
            const val NAME = "name"
            const val LOCATION = "location"
            const val COMPANY = "company"
            const val AVATAR_URL = "photo_url"
        }
    }
}