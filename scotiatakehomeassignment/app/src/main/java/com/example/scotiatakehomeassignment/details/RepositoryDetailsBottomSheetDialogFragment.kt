package com.example.scotiatakehomeassignment.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.scotiatakehomeassignment.databinding.DialogFragmentRepositoryDetailsBinding
import com.example.scotiatakehomeassignment.formatDate
import com.example.scotiatakehomeassignment.model.Repository
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RepositoryDetailsBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var binding: DialogFragmentRepositoryDetailsBinding? = null

    companion object {
        private const val PARAM_REPOSITORY = "repository"

        fun newInstance(repository: Repository) =
            RepositoryDetailsBottomSheetDialogFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(PARAM_REPOSITORY, repository)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogFragmentRepositoryDetailsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = arguments?.getParcelable<Repository>(PARAM_REPOSITORY)
        repository?.let {
            binding?.tvLastUpdated?.text = formatUpdatedAtDate(it.updatedAt)
            binding?.tvStars?.text = it.starsCount.toString()
            binding?.tvForks?.text = it.forksCount.toString()
        }
    }

    private fun formatUpdatedAtDate(rawDate: String): String {
        // Need to replace 'T' and 'Z' in the date with ' ' to make it parsable
        val dateIn = rawDate.replace('T', ' ').replace('Z', ' ')
        val patternIn = "yyyy-MM-dd hh:mm:ss"
        val patternOut = "MMM dd, yyyy hh:mm:ss aaa"
        return formatDate(patternIn, patternOut, dateIn) ?: "-"
    }
}