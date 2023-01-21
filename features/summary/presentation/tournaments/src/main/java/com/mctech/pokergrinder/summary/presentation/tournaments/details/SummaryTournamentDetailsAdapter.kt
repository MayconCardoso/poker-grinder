package com.mctech.pokergrinder.summary.presentation.tournaments.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mctech.pokergrinder.architecture.utility.SimpleItemDiffCallback
import com.mctech.pokergrinder.design.R
import com.mctech.pokergrinder.summary.domain.entities.TournamentSummary
import com.mctech.pokergrinder.summary.presentation.tournament.databinding.FragmentSummaryTournamentDetailItemBinding

internal class SummaryTournamentDetailsAdapter(
  val onTournamentClick: (tournament: TournamentSummary) -> Unit,
) : ListAdapter<TournamentSummary, SummaryTournamentDetailsAdapter.ViewHolder>(
  SimpleItemDiffCallback()
) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
    FragmentSummaryTournamentDetailItemBinding.inflate(
      LayoutInflater.from(parent.context), parent, false
    )
  )

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  inner class ViewHolder(
    private val binding: FragmentSummaryTournamentDetailItemBinding,
  ) : RecyclerView.ViewHolder(binding.root) {

    init {
      binding.root.setOnClickListener {
        onTournamentClick(getItem(absoluteAdapterPosition))
      }
    }

    fun bind(tournament: TournamentSummary) {
      binding.title.text = tournament.formattedDate()
      binding.balance.text = tournament.formattedProfit()

      // Change indicator color
      val color = ContextCompat.getColor(
        binding.root.context,
        if (tournament.profit >= 0) R.color.deposit else R.color.withdraw
      )
      binding.balance.setTextColor(color)
    }
  }
}