package com.binar.movieapp.data.local.database.user

import com.binar.movieapp.data.local.model.user.UserEntity

interface UserLocalDataSource {
    suspend fun registerUser(user: UserEntity): Long

    suspend fun getUser(username: String) : UserEntity
}

class UserLocalDataSourceImpl(private val userDao: UserDao): UserLocalDataSource {
    override suspend fun registerUser(user: UserEntity): Long {
        return userDao.registerUser(user)
    }

    override suspend fun getUser(username: String): UserEntity {
       return userDao.getUser(username)
    }
}