package com.binar.movieapp.data.local.database.user

import com.binar.movieapp.data.local.model.user.UserEntity

interface UserLocalDataSource {
    suspend fun registerUser(user: UserEntity): Long

    suspend fun updateUser(user: UserEntity): Int

    suspend fun validateUserLogin(username: String, password: String) : Boolean
    suspend fun getIfUserExists(username: String) : Boolean
    suspend fun getUserById(id: Long): UserEntity
    suspend fun getUserByUsername(username: String): UserEntity
}

class UserLocalDataSourceImpl(private val userDao: UserDao): UserLocalDataSource {
    override suspend fun registerUser(user: UserEntity): Long {
        return userDao.registerUser(user)
    }

    override suspend fun updateUser(user: UserEntity): Int {
        return userDao.updateUser(user)
    }

    override suspend fun validateUserLogin(username: String, password: String): Boolean {
       return userDao.validateUserLogin(username, password)
    }

    override suspend fun getIfUserExists(username: String): Boolean {
        return userDao.getIfUserExists(username)
    }

    override suspend fun getUserById(id: Long): UserEntity {
        return userDao.getUserById(id)
    }

    override suspend fun getUserByUsername(username: String): UserEntity {
        return userDao.getUserByUsername(username)
    }
}