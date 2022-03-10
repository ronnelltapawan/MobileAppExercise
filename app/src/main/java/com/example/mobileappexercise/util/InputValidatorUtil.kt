package com.example.mobileappexercise.util

// this is a singleton object responsible for user input validation

object InputValidatorUtil {
    fun isInputEmpty(input: String?): Boolean {
        return input.isNullOrBlank()
    }

    fun isInputSame(input: String?, oldInput: String?): Boolean {
        return input.equals(oldInput, ignoreCase = true)
    }
}