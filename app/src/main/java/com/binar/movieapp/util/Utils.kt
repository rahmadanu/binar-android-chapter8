package com.binar.movieapp.util

import android.content.Context
import android.widget.Toast

object Utils {

    var isFailure = false

    fun showFailureToast(context: Context, message: String, isError: Boolean) {
        isFailure = isError
        if (isError) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}