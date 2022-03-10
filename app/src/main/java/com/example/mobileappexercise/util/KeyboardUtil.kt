package com.example.mobileappexercise.util

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

// this is a singleton object responsible for manual keyboard hide/show logic

object KeyboardUtil {
    fun hideKeyboard(activity: Activity?) {
        activity?.let {
            val inputMethodManager = it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(it.window.currentFocus?.windowToken, 0)
        }
    }

    fun showKeyboard(activity: Activity?) {
        activity?.let {
            val inputMethodManager = it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }
    }
}