package com.kernacs.rickmorty.ui.episodes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kernacs.rickmorty.BR
import com.kernacs.rickmorty.R
import com.kernacs.rickmorty.data.entities.EpisodeEntity
import com.kernacs.rickmorty.data.entities.toTwoLineEpisode
import com.kernacs.rickmorty.databinding.EpisodeListItemBinding
import com.kernacs.rickmorty.ui.episodes.details.EpisodeDetailsFragment

class EpisodeListAdapter(
    private val values: MutableList<EpisodeEntity>
) : RecyclerView.Adapter<EpisodeListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            EpisodeListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(values[position])
    }

    override fun getItemCount(): Int = values.size

    fun setItems(episodes: List<EpisodeEntity>) {
        values.clear()
        values.addAll(episodes)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: EpisodeListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: EpisodeEntity) {
            binding.setVariable(BR.episode, item.toTwoLineEpisode())
            with(binding.root)
            {
                tag = item
                setOnClickListener {
                    val bundle = bundleOf(EpisodeDetailsFragment.ARG_EPISODE_ID to item.id)
//                    val extras =
//                        FragmentNavigatorExtras(binding.episodeInfo to "shared_element_container")
                    binding.root.findNavController()
                        .navigate(R.id.episode_detail_fragment, bundle, null, null)
                }
                binding.executePendingBindings()
            }
        }

    }
}