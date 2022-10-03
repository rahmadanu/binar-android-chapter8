package com.binar.movieapp.data.local.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class UserPreference(context: Context) {
    private val preference: SharedPreferences = context.getSharedPreferences(NAME, MODE)

    companion object {
        private const val NAME = "LoginAuth"
        private const val MODE = Context.MODE_PRIVATE
    }
    var loginKeyValue: Boolean
        get() = preference.getBoolean(
            PreferenceKey.PREF_USER_LOGIN_KEY.first,
            PreferenceKey.PREF_USER_LOGIN_KEY.second,
        )
        set(value) = preference.edit {
            this.putBoolean(PreferenceKey.PREF_USER_LOGIN_KEY.first, value)
        }
}

object PreferenceKey{
    val PREF_USER_LOGIN_KEY = Pair("PREF_USER_LOGIN_KEY", false)
}