package com.mctech.pokergrinder.summary.presentation.tournaments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mctech.pokergrinder.architecture.utility.SimpleItemDiffCallback
import com.mctech.pokergrinder.design.R
import com.mctech.pokergrinder.summary.domain.entities.TournamentSummary
import com.mctech.pokergrinder.summary.presentation.tournament.databinding.FragmentSummaryTournamentItemBinding

internal class SummaryTournamentListAdapter :
  ListAdapter<TournamentSummary, SummaryTournamentListAdapter.ViewHolder>(SimpleItemDiffCallback()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
    FragmentSummaryTournamentItemBinding.inflate(
      LayoutInflater.from(parent.context), parent, false
    )
  )

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  inner class ViewHolder(
    private val binding: FragmentSummaryTournamentItemBinding,
  ) : RecyclerView.ViewHolder(binding.root) {

    fun bind(tournament: TournamentSummary) {
      binding.title.text = tournament.title
      binding.countBuyIn.text = binding.root.context.getString(
        com.mctech.pokergrinder.localization.R.string.count_buy_in,
        tournament.tournaments,
      )
      binding.roi.text = tournament.formattedRoi()
      binding.cash.text = tournament.formattedCash()
      binding.buyIn.text = tournament.formattedBuyIn()
      binding.balance.text = tournament.formattedProfit()

      // Change indicator color
      val color = ContextCompat.getColor(
        binding.root.context,
        if (tournament.profit >= 0) R.color.deposit else R.color.withdraw
      )
      binding.indicator.setBackgroundColor(color)
      binding.balance.setTextColor(color)
    }
  }
}