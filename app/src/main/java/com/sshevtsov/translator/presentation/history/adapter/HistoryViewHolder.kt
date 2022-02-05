package com.sshevtsov.translator.presentation.history.adapter

import androidx.recyclerview.widget.RecyclerView
import com.sshevtsov.translator.databinding.FragmentHistoryListItemBinding
import com.sshevtsov.translator.domain.model.history.History
import com.sshevtsov.translator.presentation.history.util.TimestampFormatter

class HistoryViewHolder(
    private val binding: FragmentHistoryListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(history: History) {
        binding.wordTextView.text = history.word
        binding.timestampTextView.text =
            TimestampFormatter.formatTimestampToHistoryString(history.timestamp)
    }
}