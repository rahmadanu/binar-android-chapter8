package com.binar.movieapp.data.local.preference

interface UserPreferenceDataSource {
    fun getIfUserLogin(): Boolean

    fun setIfUserLogin(userLoggedIn: Boolean)
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

}