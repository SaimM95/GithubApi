package com.example.scotiatakehomeassignment.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.scotiatakehomeassignment.R
import com.example.scotiatakehomeassignment.databinding.FragmentSearchBinding
import com.example.scotiatakehomeassignment.details.RepositoryDetailsBottomSheetDialogFragment
import com.example.scotiatakehomeassignment.hideKeyboard
import com.example.scotiatakehomeassignment.model.Repository
import com.example.scotiatakehomeassignment.network.Status
import com.squareup.picasso.Picasso

class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchViewModel

    private var binding: FragmentSearchBinding? = null
    private var searchResultAdapter: SearchResultAdapter = SearchResultAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        ).get(SearchViewModel::class.java)

        binding?.rvSearchResults?.apply {
            searchResultAdapter.onItemClickedListener = { repository ->
                RepositoryDetailsBottomSheetDialogFragment.newInstance(repository)
                    .show(requireFragmentManager(), null)
            }
            adapter = searchResultAdapter
            itemAnimator = null
        }

        binding?.btnSearch?.setOnClickListener {
            performSearch()
            hideKeyboard(requireContext(), it)
        }

        binding?.etUsername?.setOnKeyListener { v, keyCode, event ->
            if ((event.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                performSearch()
                hideKeyboard(requireContext(), v)
                return@setOnKeyListener true
            }

            false
        }
    }

    private fun performSearch() {
        val username = binding?.etUsername?.text?.toString()
        if (username?.isNotEmpty() == true) {
            viewModel.getRepositories(username)
                .observe(viewLifecycleOwner, Observer { resource ->
                    when (val status = resource.status) {
                        Status.LOADING -> {
                            showLoading(true)
                        }
                        else -> {
                            showLoading(false)

                            when (status) {
                                Status.SUCCESS -> {
                                    val repositories = resource.data as List<Repository>
                                    showAvatar(repositories[0].owner.avatarUrl)
                                    showRepositories(repositories)
                                }
                                Status.EMPTY -> {
                                    hideContent()
                                    Toast.makeText(
                                        requireContext(), "No repositories found",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                Status.ERROR -> {
                                    hideContent()
                                    Toast.makeText(
                                        requireContext(), "Username not found",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
                })
        }
    }

    private fun showLoading(loading: Boolean) {
        binding?.progressBar?.visibility = if (loading) View.VISIBLE else View.GONE
    }

    private fun showAvatar(avatarUrl: String) {
        binding?.ivAvatar?.apply {
            visibility = View.VISIBLE

            Picasso.with(requireContext())
                .load(avatarUrl)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.ic_error)
                .into(this)
        }
    }

    private fun showRepositories(repositories: List<Repository>) {
        searchResultAdapter.submitList(repositories.toMutableList())
    }

    private fun hideContent() {
        binding?.ivAvatar?.visibility = View.GONE
        showRepositories(emptyList())
    }
}