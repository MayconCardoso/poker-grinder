package com.mctech.pokergrinder.grind.presentation.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mctech.pokergrinder.architecture.utility.SimpleItemDiffCallback
import com.mctech.pokergrinder.design.R
import com.mctech.pokergrinder.grind.presentation.list.GrindState
import com.mctech.pokergrinder.grind.presentation.list.databinding.FragmentGrindItemBinding

internal class GrindAdapter(
  private val eventConsumer: GrindAdapterConsumer,
) :
  ListAdapter<GrindState, GrindAdapter.ViewHolder>(
    SimpleItemDiffCallback(
      itemsTheSame = { oldItem, newItem ->
        oldItem.session.id == newItem.session.id
      }
    ),
  ) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
    FragmentGrindItemBinding.inflate(
      LayoutInflater.from(parent.context), parent, false
    )
  )

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  inner class ViewHolder(
    private val binding: FragmentGrindItemBinding,
  ) : RecyclerView.ViewHolder(binding.root) {

    init {
      binding.root.setOnClickListener {
        onItemClicked(getItem(absoluteAdapterPosition))
      }
    }

    fun bind(state: GrindState) {
      val context = binding.root.context

      binding.title.text = state.title
      binding.roi.text = state.roi
      binding.cash.text = state.cash
      binding.buyIn.text = state.buyIn
      binding.avgBuyIn.text = state.avgBuyIn
      binding.balance.text = state.profit
      binding.countBuyIn.text = context.getString(
        com.mctech.pokergrinder.localization.R.string.count_buy_in,
        state.tournaments,
      )

      // Change indicator color
      val color = ContextCompat.getColor(
        binding.root.context,
        if (state.session.isInProfit()) R.color.deposit else R.color.withdraw
      )
      binding.indicator.setBackgroundColor(color)
      binding.balance.setTextColor(color)
    }
  }

  private fun onItemClicked(item: GrindState) {
    eventConsumer.consume(GrindAdapterConsumerEvent.SessionClicked(item))
  }
}