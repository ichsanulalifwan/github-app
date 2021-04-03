package com.dicoding.picodiploma.githubapp.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.dicoding.picodiploma.githubapp.db.DatabaseContract.UserColoumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object {

        private const val DATABASE_NAME = "githubapp"

        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_NOTE = "CREATE TABLE $TABLE_NAME" +
                " (${DatabaseContract.UserColoumns.USERNAME} TEXT PRIMARY KEY," +
                " ${DatabaseContract.UserColoumns.NAME} TEXT NOT NULL," +
                " ${DatabaseContract.UserColoumns.LOCATION} TEXT NOT NULL," +
                " ${DatabaseContract.UserColoumns.COMPANY} TEXT NOT NULL)," +
                " ${DatabaseContract.UserColoumns.FOLLOWER} TEXT NOT NULL)," +
                " ${DatabaseContract.UserColoumns.FOLLOWING} TEXT NOT NULL)," +
                " ${DatabaseContract.UserColoumns.AVATAR_URL} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_NOTE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}