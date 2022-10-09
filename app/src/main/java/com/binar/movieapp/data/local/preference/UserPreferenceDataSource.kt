package com.binar.movieapp.data.local.preference

interface UserPreferenceDataSource {
    fun getIfUserLogin(): Boolean

    fun setIfUserLogin(userLoggedIn: Boolean)

    fun setUserId(id: Long)

    fun getUserId(): Long
}

class UserPreferenceDataSourceImpl(
    private val userPreference: UserPreference
): UserPreferenceDataSource {
    override fun getIfUserLogin(): Boolean {
        return userPreference.loginKeyValue
    }

    override fun setIfUserLogin(userLoggedIn: Boolean) {
        userPreference.loginKeyValue = userLoggedIn
    }

    override fun setUserId(id: Long) {
        userPreference.userIdKeyValue = id
    }

    override fun getUserId(): Long {
        return userPreference.userIdKeyValue
    }

}