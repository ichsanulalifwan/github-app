package com.dicoding.picodiploma.githubapp.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorite_user_table")
data class FavUser(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "username") var username: String,
    @ColumnInfo(name = "name")var name: String?,
    @ColumnInfo(name = "location")var location: String?,
    @ColumnInfo(name = "company")var company: String?,
    @ColumnInfo(name = "avatar")var avatar: String?
)
