package com.binar.movieapp.data.local.model.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_information")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var userId: Long = 0,

    @ColumnInfo(name = "username")
    var username: String? = "",

    @ColumnInfo(name = "email")
    var email: String,

    @ColumnInfo(name = "password")
    var password: String? = ""
)
