package com.example.mobileappexercise.ui.fragment

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mobileappexercise.R
import com.example.mobileappexercise.databinding.FragmentSearchPhotoBinding
import com.example.mobileappexercise.ui.adapter.PhotoAdapter
import com.example.mobileappexercise.ui.viewmodel.MainViewModel
import com.example.mobileappexercise.util.InputValidatorUtil
import com.example.mobileappexercise.util.KEY_RECYCLER_STATE
import com.example.mobileappexercise.util.KeyboardUtil
import com.example.mobileappexercise.util.LoadingStatus
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchPhotoFragment : Fragment(R.layout.fragment_search_photo), PhotoAdapter.CustomCallback {

    private var _binding: FragmentSearchPhotoBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by viewModels()

    @Inject
    lateinit var photoAdapter: PhotoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSearchPhotoBinding.bind(view)

        // this is for the clear button inside the search bar
        // that will reset the UI
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
                    KeyboardUtil.hideKeyboard(requireActivity())
                    mainViewModel.reset()
                    mainViewModel.fetchFlickrPhotos(searchTerm)
                }
            }

            true
        }

        binding.recycler.adapter = photoAdapter
        binding.recycler.setHasFixedSize(true)

        // this is to notify the user if the app is loading or not
        mainViewModel.loadingStatus.observe(viewLifecycleOwner) {
            binding.progress.visibility = when (it) {
                LoadingStatus.LOADING -> {
                    binding.txtNoPhoto.visibility = View.GONE

                    View.VISIBLE
                }
                else -> {
                    if (it == LoadingStatus.FAIL) {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.loading_failed),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }

                    View.GONE
                }
            }
        }

        // this is for the recyclerview as well as the empty state logic
        mainViewModel.photoList.observe(viewLifecycleOwner) {
            photoAdapter.submitList(it.toMutableList())

            if (it.isEmpty()) {
                binding.recycler.visibility = View.INVISIBLE
                binding.txtNoPhoto.visibility = View.VISIBLE
            } else {
                binding.txtNoPhoto.visibility = View.GONE
                binding.recycler.visibility = View.VISIBLE
            }
        }

        // this is responsible for restoring recyclerView state
        // after handling configuration changes
        savedInstanceState?.let { bundle ->
            binding.recycler.layoutManager?.onRestoreInstanceState(
                bundle.getParcelable(KEY_RECYCLER_STATE)
            )
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelable(
            KEY_RECYCLER_STATE,
            binding.recycler.layoutManager?.onSaveInstanceState()
        )
    }

    override fun onDestroyView() {
        _binding = null

        super.onDestroyView()
    }

    // this is to reset the UI as well as the ViewModel properties
    private fun reset() {
        KeyboardUtil.hideKeyboard(requireActivity())
        binding.txtSearch.setText("")
        binding.txtSearch.clearFocus()
        mainViewModel.reset()
    }

    // this is the function responsible for the pagination
    override fun onLoadMore() {
        mainViewModel.onLoadMore()
    }
}