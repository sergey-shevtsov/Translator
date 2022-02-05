package com.sshevtsov.translator.presentation.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sshevtsov.translator.databinding.FragmentHistoryListItemBinding
import com.sshevtsov.translator.domain.model.history.History

class HistoryAdapter(
    private val data: List<History>
) : RecyclerView.Adapter<HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder =
        HistoryViewHolder(
            FragmentHistoryListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) =
        holder.bind(data[position])

    override fun getItemCount(): Int =
        data.size

}