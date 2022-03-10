package com.example.mobileappexercise.ui.activity

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileappexercise.R
import com.example.mobileappexercise.databinding.ActivityMainBinding
import com.example.mobileappexercise.ui.adapter.PhotoAdapter
import com.example.mobileappexercise.ui.viewmodel.MainViewModel
import com.example.mobileappexercise.util.InputValidatorUtil
import com.example.mobileappexercise.util.KeyboardUtil
import com.example.mobileappexercise.util.LoadingStatus
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

// since this is just a single screen application
// I decided to put my UI inside this activity
// I would probably use fragments otherwise

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), PhotoAdapter.CustomCallback {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels()

    @Inject
    lateinit var photoAdapter: PhotoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // this is for the X button inside the search bar
        binding.txtSearchLayout.setEndIconOnClickListener {
            reset()
        }

        // this is for the search button on the virtual keyboard
        binding.txtSearch.setOnEditorActionListener { textView, i, _ ->
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                val searchTerm = textView.text.toString()

                if (InputValidatorUtil.isInputEmpty(searchTerm)) {
                    reset()
                } else if (!InputValidatorUtil.isInputSame(searchTerm, mainViewModel.searchTerm)) {
                    KeyboardUtil.hideKeyboard(this)
                    mainViewModel.reset()
                    mainViewModel.fetchFlickrPhotos(searchTerm)
                }
            }

            true
        }

        binding.recycler.adapter = photoAdapter
        binding.recycler.setHasFixedSize(true)

        // this is to notify the user if the app is loading or not
        mainViewModel.loadingStatus.observe(this) {
            binding.progress.visibility = when (it) {
                LoadingStatus.LOADING -> {
                    binding.txtNoPhoto.visibility = View.GONE

                    View.VISIBLE
                }
                else -> {
                    if (it == LoadingStatus.FAIL) {
                        Toast.makeText(this, getString(R.string.loading_failed), Toast.LENGTH_SHORT)
                            .show()
                    }

                    View.GONE
                }
            }
        }

        // this is for the recyclerview as well as the empty state logic
        mainViewModel.photoList.observe(this) {
            photoAdapter.submitList(it.toMutableList())

            if (it.isEmpty()) {
                binding.recycler.visibility = View.INVISIBLE
                binding.txtNoPhoto.visibility = View.VISIBLE
            } else {
                binding.txtNoPhoto.visibility = View.GONE
                binding.recycler.visibility = View.VISIBLE
            }
        }
    }

    // this is to reset the UI as well as the ViewModel properties
    private fun reset() {
        KeyboardUtil.hideKeyboard(this)
        binding.txtSearch.setText("")
        binding.txtSearch.clearFocus()
        mainViewModel.reset()
    }

    // this is the function responsible for the pagination
    override fun onLoadMore() {
        mainViewModel.onLoadMore()
    }
}