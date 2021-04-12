package com.dicoding.picodiploma.githubapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.picodiploma.githubapp.db.entity.FavUser


@Database(entities = [FavUser::class], exportSchema = false, version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favDao(): FavUserDao

    companion object {

        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(AppDatabase::class) {
                    instance = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "FAV_USER_DB"
                    ).build()
                }
            }
            return instance
        }
    }
}