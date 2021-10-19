package com.kernacs.rickmorty.ui.characters.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kernacs.rickmorty.BR
import com.kernacs.rickmorty.data.entities.CharacterEntity
import com.kernacs.rickmorty.databinding.CharacterListItemBinding

class CharacterListRecyclerViewAdapter(
    private val values: MutableList<CharacterEntity>,
    private val onClickListener: View.OnClickListener? = null
) : RecyclerView.Adapter<CharacterListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CharacterListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.bind(item)
    }

    override fun getItemCount() = values.size

    fun addItems(characters: List<CharacterEntity>) {
        values.addAll(characters)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: CharacterListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CharacterEntity) {
            binding.setVariable(BR.character, item)

            binding.root.setOnClickListener {
                with(binding.root) {
                    tag = item
                    onClickListener?.let { setOnClickListener(onClickListener) }
                }
                binding.executePendingBindings()
            }
        }
    }
}