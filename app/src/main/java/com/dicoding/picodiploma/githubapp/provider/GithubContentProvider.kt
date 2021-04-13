package com.dicoding.picodiploma.githubapp.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.dicoding.picodiploma.githubapp.db.AppDatabase
import com.dicoding.picodiploma.githubapp.db.FavUserDao

class GithubContentProvider : ContentProvider() {

    companion object {
        private const val FAV_USER = 1
        private const val TABLE = "favorite_user_table"
        private const val AUTHORITY = "com.dicoding.picodiploma.githubapp.provider"
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            // content://com.dicoding.picodiploma.githubapp.provider/favorite_user_table
            uriMatcher.addURI(AUTHORITY, TABLE, FAV_USER)
        }
    }

    private val favUserDao: FavUserDao by lazy {
        AppDatabase.getDatabase(requireNotNull(context))!!.favDao()
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        throw UnsupportedOperationException()
    }

    override fun getType(uri: Uri): String? {
        throw UnsupportedOperationException()
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw UnsupportedOperationException()
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor {
        return when (uriMatcher.match(uri)) {
            FAV_USER -> favUserDao.cursorGetData()
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        throw UnsupportedOperationException()
    }
}