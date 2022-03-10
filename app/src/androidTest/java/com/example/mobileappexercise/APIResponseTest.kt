package com.example.mobileappexercise

import com.example.mobileappexercise.repository.FlickrRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

// this is my first time implementing an instrumented test
// so im not really sure if this is the right way

@HiltAndroidTest
class APIResponseTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var flickrRepository: FlickrRepository

    @Before
    fun init() {
        hiltRule.inject()
    }

    // this test checks if the API response is correct based on the model class
    @Test
    fun successfulResponseTest() {
        runBlocking {
            val response = flickrRepository.fetchFlickrPhotos("Test", 1)

            assertTrue(response.isSuccessful)
            assertNotNull(response.body())
            assertNotNull(response.body()!!.photos)
            assertEquals(response.body()!!.stat, "ok")
        }
    }

    // this test checks if the API pagination is working
    @Test
    fun pageCountTest() {
        var currentPage = 1
        var finalPage = 1

        repeat(2) {
            runBlocking {
                val response = flickrRepository.fetchFlickrPhotos("Test", currentPage)

                if (response.isSuccessful && response.body() != null) {
                    currentPage += 1
                    finalPage = response.body()!!.photos!!.page!!
                }

                delay(250)
            }
        }

        assertEquals(finalPage, (currentPage - 1))
    }
}