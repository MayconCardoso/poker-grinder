package com.mctech.pokergrinder.grind.presentation.grind_details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mctech.pokergrinder.architecture.utility.SimpleItemDiffCallback
import com.mctech.pokergrinder.design.R
import com.mctech.pokergrinder.grind.domain.entities.SessionTournament
import com.mctech.pokergrinder.grind.presentation.databinding.ActivityGrindDetailsTournamentBinding

internal class GrindDetailsAdapter(
  private val eventConsumer: GrindDetailsConsumer,
) :
  ListAdapter<SessionTournament, GrindDetailsAdapter.ViewHolder>(SimpleItemDiffCallback()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
    ActivityGrindDetailsTournamentBinding.inflate(
      LayoutInflater.from(parent.context), parent, false
    )
  )

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  inner class ViewHolder(
    private val binding: ActivityGrindDetailsTournamentBinding,
  ) : RecyclerView.ViewHolder(binding.root) {

    init {
      binding.root.setOnClickListener {
        onItemClicked(getItem(absoluteAdapterPosition))
      }
    }


    fun bind(item: SessionTournament) {
      binding.title.text = item.title
      binding.buyIn.text = item.formattedBuyIn()
      binding.profit.text = item.formattedProfit()

      // Change indicator color
      val color = ContextCompat.getColor(
        binding.root.context,
        if (item.computesProfit() >= 0) R.color.deposit else R.color.withdraw
      )
      binding.indicator.setBackgroundColor(color)
      binding.profit.setTextColor(color)
    }
  }

  private fun onItemClicked(item: SessionTournament) {
    eventConsumer.consume(GrindDetailsConsumerEvent.TournamentClicked(item))
  }
}