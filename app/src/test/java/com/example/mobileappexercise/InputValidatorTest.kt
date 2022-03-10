package com.example.mobileappexercise

import com.example.mobileappexercise.util.InputValidatorUtil
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class InputValidatorTest {

    @Test
    fun inputEmptyOrNotTest() {
        assertTrue(
            InputValidatorUtil.isInputEmpty(
                ""
            )
        )

        assertFalse(
            InputValidatorUtil.isInputEmpty(
                "Picture"
            )
        )
    }

    @Test
    fun inputSameOrNotTest() {
        assertTrue(
            InputValidatorUtil.isInputSame(
                "OldInput", "OldInput"
            )
        )

        assertFalse(
            InputValidatorUtil.isInputSame(
                "", "OldInput"
            )
        )
    }
}