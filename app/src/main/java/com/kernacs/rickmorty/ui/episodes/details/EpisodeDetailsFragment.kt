package com.kernacs.rickmorty.ui.episodes.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kernacs.rickmorty.R
import com.kernacs.rickmorty.data.entities.EpisodeEntity
import com.kernacs.rickmorty.data.entities.toTwoLineEpisode
import com.kernacs.rickmorty.databinding.FragmentEpisodeDetailsBinding
import com.kernacs.rickmorty.ui.characters.list.CharacterListRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodeDetailsFragment : Fragment() {

    private val viewModel: EpisodeDetailsViewModel by viewModels()

    private lateinit var binding: FragmentEpisodeDetailsBinding

    private val list: RecyclerView
        get() = binding.charactersList

    private val charactersAdapter: CharacterListRecyclerViewAdapter
        get() = list.adapter as CharacterListRecyclerViewAdapter
    private lateinit var episode: EpisodeEntity


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_episode_details, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.episodeData.observe(this as LifecycleOwner, {
            episode = it.toTwoLineEpisode()
            binding.episode = episode
            binding.progress.isVisible = false
        })
        viewModel.isLoading.observe(this as LifecycleOwner, {
            binding.progress.isVisible = true
            binding.empty.emptyContainer.isVisible = false
        })
        viewModel.error.observe(this as LifecycleOwner, { error ->
            error?.let {
                Snackbar.make(view, error, Snackbar.LENGTH_LONG).show()
            }
            binding.progress.isVisible = false
            checkEmptyView()
        })

        list.layoutManager = LinearLayoutManager(context)
        list.adapter = CharacterListRecyclerViewAdapter(mutableListOf())

        viewModel.characters.observe(viewLifecycleOwner, {
            Log.d(TAG, it.toString())
            charactersAdapter.addItems(it)
            binding.progress.isVisible = false
            checkEmptyView()
        })

        arguments?.let {
            val id = it.getInt(ARG_EPISODE_ID)
            viewModel.getEpisode(id)
        }
    }

    private fun checkEmptyView() {
        binding.apply {
            if (charactersAdapter.itemCount == 0) {
                empty.emptyContainer.isVisible = true
                empty.retryButton.setOnClickListener {
                    episode?.characters?.let { ids ->
                        viewModel.getCharacters(ids)
                    }
                }
                binding.charactersList.isVisible = false
            } else {
                binding.charactersList.isVisible = true
                binding.empty.emptyContainer.isVisible = false
            }
        }
    }

    companion object {
        /**
         * The fragment argument representing the character ID that this fragment
         * represents.
         */
        const val ARG_EPISODE_ID = "episode_id"
        const val TAG = "CharacterDetailFragment"
    }
}