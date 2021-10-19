package com.kernacs.rickmorty.ui.favourites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kernacs.rickmorty.base.BaseFragment
import com.kernacs.rickmorty.databinding.FragmentFavouriteCharactersBinding
import com.kernacs.rickmorty.ui.characters.list.CharacterListRecyclerViewAdapter
import com.kernacs.rickmorty.ui.episodes.details.EpisodeDetailsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteCharactersFragment : BaseFragment() {

    private val viewModel: FavouriteCharactersViewModel by viewModels()

    private lateinit var binding: FragmentFavouriteCharactersBinding
    private val list: RecyclerView
        get() = binding.charactersList

    private val charactersAdapter: CharacterListRecyclerViewAdapter
        get() = list.adapter as CharacterListRecyclerViewAdapter

    override val trackedScreenName: String
        get() = "FavouriteCharacters"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentFavouriteCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
            Log.d(EpisodeDetailsFragment.TAG, it.toString())
            charactersAdapter.addItems(it)
            binding.progress.isVisible = false
            checkEmptyView()
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.getCharacters()
    }

    private fun checkEmptyView() {
        binding.apply {
            if (charactersAdapter.itemCount == 0) {
                empty.emptyContainer.isVisible = true
                empty.retryButton.setOnClickListener {
                    viewModel.getCharacters()

                }
                binding.charactersList.isVisible = false
            } else {
                binding.charactersList.isVisible = true
                binding.empty.emptyContainer.isVisible = false
            }
        }
    }
}