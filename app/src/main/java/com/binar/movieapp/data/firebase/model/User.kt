package com.binar.movieapp.data.firebase.model

data class User(
    var id: String = "",
    val username: String = "",
    val email: String = "",
    val fullName: String = "",
    val dateOfBirth: String = "",
    val address: String = "",
    val profileImage: String = "",
)