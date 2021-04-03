package com.dicoding.picodiploma.githubapp.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.dicoding.picodiploma.githubapp.db.DatabaseContract.UserColoumns.Companion.TABLE_NAME
import com.dicoding.picodiploma.githubapp.db.DatabaseContract.UserColoumns.Companion.USERNAME

class FavUserHelper(context: Context) {

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private lateinit var dataBaseHelper: DatabaseHelper
        private lateinit var database: SQLiteDatabase
        private var INSTANCE: FavUserHelper? = null

        fun getInstance(context: Context): FavUserHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: FavUserHelper(context)
            }
    }

    init {
        dataBaseHelper = DatabaseHelper(context)
    }

    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    fun close() {
        dataBaseHelper.close()

        if (database.isOpen)
            database.close()
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$USERNAME COLLATE LOCALIZED ASC")
    }

    fun queryById(username: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$USERNAME LIKE ?",
            arrayOf("%$username%"),
            null,
            null,
            null,
            null)
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun update(username: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$USERNAME = ?", arrayOf(username))
    }

    fun deleteById(username: String): Int {
        return database.delete(DATABASE_TABLE, "$USERNAME = '$username'", null)
    }
}