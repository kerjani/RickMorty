package com.kernacs.rickmorty.ui.characters.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kernacs.rickmorty.R
import com.kernacs.rickmorty.data.entities.CharacterEntity
import com.kernacs.rickmorty.databinding.FragmentItemDetailBinding
import com.kernacs.rickmorty.ui.episodes.EpisodeListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {

    private val viewModel: CharacterDetailsViewModel by viewModels()

    private lateinit var binding: FragmentItemDetailBinding

    private val episodesList: RecyclerView
        get() = binding.characterDetails.episodesList

    private val episodesAdapter: EpisodeListAdapter
        get() = episodesList.adapter as EpisodeListAdapter

    private var isFavourite = false
    private lateinit var currentCharacter: CharacterEntity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_item_detail, container, false)
        binding.lifecycleOwner = this
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.characterData.observe(this as LifecycleOwner, {
            currentCharacter = it
            binding.characterDetails.character = currentCharacter
            binding.characterDetails.progress.isVisible = false
            checkEmptyView()
        })
        viewModel.isLoading.observe(this as LifecycleOwner, {
            binding.characterDetails.progress.isVisible = true
            binding.characterDetails.empty.emptyContainer.isVisible = false
        })
        viewModel.error.observe(this as LifecycleOwner, { error ->
            error?.let {
                Snackbar.make(view, error, Snackbar.LENGTH_LONG).show()
            }
            binding.characterDetails.progress.isVisible = false
            checkEmptyView()
        })

        episodesList.layoutManager = LinearLayoutManager(context)
        episodesList.adapter = EpisodeListAdapter(mutableListOf())

        viewModel.episodes.observe(viewLifecycleOwner, {
            Log.d(TAG, it.toString())
            episodesAdapter.setItems(it)
            binding.characterDetails.progress.isVisible = false
            checkEmptyView()
        })
        downloadData()
        viewModel.isFavourite.observe(this as LifecycleOwner, {
            this.isFavourite = it
            binding.characterDetails.favourite.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    if (isFavourite) R.drawable.ic_star else R.drawable.ic_star_outline
                )
            )
            binding.characterDetails.isFavourite = it

        })
        binding.characterDetails.favourite.setOnClickListener {

            if (isFavourite) {
                binding.characterDetails.favourite.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_star_outline
                    )
                )
                isFavourite = false
            } else if (!isFavourite) {
                binding.characterDetails.favourite.setImageDrawable(
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_star)
                )
                isFavourite = true
            }

            viewModel.saveFavourite(currentCharacter.id, isFavourite)

        }
    }

    private fun downloadData() {
        arguments?.let {
            val id = it.getInt(ARG_ITEM_ID)
            viewModel.getCharacterData(id)
        }
    }

    private fun checkEmptyView() {
        binding.apply {
            if (episodesAdapter.itemCount == 0) {
                characterDetails.empty.emptyContainer.isVisible = true
                characterDetails.empty.retryButton.setOnClickListener { downloadData() }
                binding.characterDetails.episodesList.isVisible = false
            } else {
                binding.characterDetails.episodesList.isVisible = true
                binding.characterDetails.empty.emptyContainer.isVisible = false
            }
        }
    }

    companion object {
        /**
         * The fragment argument representing the character ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
        const val TAG = "CharacterDetailFragment"
    }

}