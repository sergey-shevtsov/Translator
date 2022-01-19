package com.sshevtsov.translator.presentation.search.adapter

import androidx.recyclerview.widget.RecyclerView
import com.sshevtsov.translator.databinding.FragmentSearchListItemBinding
import com.sshevtsov.translator.domain.model.DataModel

class SearchViewHolder(
    private val binding: FragmentSearchListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(dataModel: DataModel) {
        binding.titleTextView.text = dataModel.text
        binding.translationTextView.text = dataModel.meanings[0].translation.text
    }

}