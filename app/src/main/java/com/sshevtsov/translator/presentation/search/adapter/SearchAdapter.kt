package com.sshevtsov.translator.presentation.search.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sshevtsov.translator.databinding.FragmentSearchListItemBinding
import com.sshevtsov.translator.domain.model.DataModel

class SearchAdapter(
    private var data: List<DataModel> = emptyList(),
    private val listener: (dataModel: DataModel) -> Unit
) : RecyclerView.Adapter<SearchViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<DataModel>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder =
        SearchViewHolder(
            FragmentSearchListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) =
        holder.bind(data[position], listener)

    override fun getItemCount(): Int = data.size

}