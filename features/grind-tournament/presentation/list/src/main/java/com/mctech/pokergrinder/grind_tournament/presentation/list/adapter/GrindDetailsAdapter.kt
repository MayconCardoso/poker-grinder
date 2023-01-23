package com.mctech.pokergrinder.grind_tournament.presentation.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mctech.pokergrinder.architecture.utility.SimpleItemDiffCallback
import com.mctech.pokergrinder.design.R
import com.mctech.pokergrinder.grind_tournament.domain.entities.SessionTournament
import com.mctech.pokergrinder.grind_tournament.presentation.list.databinding.FragmentGrindTournamentsItemBinding

internal class GrindDetailsAdapter(
  private val eventConsumer: GrindDetailsConsumer,
  private val enableContextMenu: Boolean = true,
) :
  ListAdapter<SessionTournament, GrindDetailsAdapter.ViewHolder>(SimpleItemDiffCallback()) {

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
      // Gets the context.
      val context = binding.root.context

      // Sets click on the item,
      binding.root.setOnClickListener {
        onItemClicked(getItem(absoluteAdapterPosition))
      }

      // Sets context menu on clicked item.
      if (enableContextMenu) {
        binding.root.setOnCreateContextMenuListener { menu, _, _ ->
          // Gets clicked tournament.
          val clickedTournament = getItem(absoluteAdapterPosition)

          // Set title on the menu
          menu.setHeaderTitle(clickedTournament.title)

          // Creates re enter button.
          val reEnter = menu.add(0,
            1,
            0,
            context.getString(com.mctech.pokergrinder.localization.R.string.re_enter))
          reEnter.setOnMenuItemClickListener {
            eventConsumer.consume(GrindDetailsConsumerEvent.DuplicateClicked(clickedTournament))
            true
          }
        }
      }
    }
  }

  private fun onItemClicked(item: SessionTournament) {
    eventConsumer.consume(GrindDetailsConsumerEvent.TournamentClicked(item))
  }
}