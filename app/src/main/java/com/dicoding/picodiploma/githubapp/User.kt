package com.dicoding.picodiploma.githubapp

data class User (
    var username: String? = null,
    val name: String? = null,
    val location: String? = null,
    val repository: String? = null,
    val company: String? = null,
    val followers: String? = null,
    val following: String? = null,
    var avatar: String? = null
)
