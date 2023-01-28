package com.mctech.pokergrinder.ranges.presentation.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mctech.pokergrinder.architecture.utility.SimpleItemDiffCallback
import com.mctech.pokergrinder.ranges.domain.entities.Range
import com.mctech.pokergrinder.ranges.presentation.list.databinding.FragmentRangesItemBinding

internal class RangesAdapter(
  private val eventConsumer: RangeAdapterConsumer,
) :
  ListAdapter<Range, RangesAdapter.ViewHolder>(SimpleItemDiffCallback()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
    FragmentRangesItemBinding.inflate(
      LayoutInflater.from(parent.context), parent, false
    )
  )

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  inner class ViewHolder(
    private val binding: FragmentRangesItemBinding,
  ) : RecyclerView.ViewHolder(binding.root) {

    init {
      binding.root.setOnClickListener {
        onItemClicked(getItem(absoluteAdapterPosition))
      }
    }

    fun bind(range: Range) {
      binding.title.text = range.name
    }
  }

  private fun onItemClicked(item: Range) {
    eventConsumer.consume(RangeAdapterConsumerEvent.Clicked(item))
  }
}