package com.mctech.pokergrinder.grind.presentation.flip_creation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mctech.pokergrinder.architecture.utility.SimpleItemDiffCallback
import com.mctech.pokergrinder.design.R
import com.mctech.pokergrinder.grind.presentation.flip_creation.databinding.FragmentGrindTournamentsItemBinding
import com.mctech.pokergrinder.grind_tournament.domain.entities.SessionTournament

internal class RegisterFlipTournamentAdapter(
  private val eventConsumer: RegisterFlipTournamentConsumer,
) :
  ListAdapter<SessionTournament, RegisterFlipTournamentAdapter.ViewHolder>(SimpleItemDiffCallback()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
    FragmentGrindTournamentsItemBinding.inflate(
      LayoutInflater.from(parent.context), parent, false
    )
  )

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  inner class ViewHolder(
    private val binding: FragmentGrindTournamentsItemBinding,
  ) : RecyclerView.ViewHolder(binding.root) {

    init {
      setupViewListeners()
    }

    fun bind(item: SessionTournament) {
      binding.title.text = item.title
      binding.buyIn.text = item.formattedBuyIn()
      binding.profit.text = item.formattedBalance()
      binding.cash.text = item.formattedProfit()

      // Change indicator color
      val color = ContextCompat.getColor(
        binding.root.context,
        if (item.computesBalance() >= 0) R.color.deposit else R.color.withdraw
      )
      binding.indicator.setBackgroundColor(color)
      binding.profit.setTextColor(color)
    }

    private fun setupViewListeners() {
      // Sets click on the item,
      binding.root.setOnClickListener {
        onItemClicked(getItem(absoluteAdapterPosition))
      }
    }
  }

  private fun onItemClicked(item: SessionTournament) {
    eventConsumer.consume(RegisterFlipTournamentConsumerEvent.TournamentClicked(item))
  }
}