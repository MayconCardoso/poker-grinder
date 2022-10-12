package com.mctech.pokergrinder.grind.presentation.grind_gameplay

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mctech.pokergrinder.architecture.utility.SimpleItemDiffCallback
import com.mctech.pokergrinder.deck.presentation.card_picker.loadCardImage
import com.mctech.pokergrinder.design.R
import com.mctech.pokergrinder.grind.domain.entities.SessionTournamentFlip
import com.mctech.pokergrinder.grind.presentation.databinding.FragmentGrindDetailsTournamentFlipBinding

internal class GrindDetailsGameplayAdapter :
  ListAdapter<SessionTournamentFlip, GrindDetailsGameplayAdapter.ViewHolder>(SimpleItemDiffCallback()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
    FragmentGrindDetailsTournamentFlipBinding.inflate(
      LayoutInflater.from(parent.context), parent, false
    )
  )

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  inner class ViewHolder(
    private val binding: FragmentGrindDetailsTournamentFlipBinding,
  ) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: SessionTournamentFlip) {
      // Gets context
      val context = binding.root.context

      // Resole tournament title
      binding.title.text = item.tournament

      // Change indicator color
      val color = ContextCompat.getColor(
        binding.root.context,
        if (item.won) R.color.deposit else R.color.withdraw
      )
      binding.indicator.setBackgroundColor(color)

      // Hero cards
      val heroCards = item.heroHand.split("-")
      binding.hero1.setImageDrawable(heroCards.first().loadCardImage(context))
      binding.hero2.setImageDrawable(heroCards.last().loadCardImage(context))

      // Hero villain
      val villainCards = item.villainHand.split("-")
      binding.villain1.setImageDrawable(villainCards.first().loadCardImage(context))
      binding.villain2.setImageDrawable(villainCards.last().loadCardImage(context))

      // Board
      val boardCards = item.board.split("-")
      binding.board1.setImageDrawable(boardCards[0].loadCardImage(context))
      binding.board2.setImageDrawable(boardCards[1].loadCardImage(context))
      binding.board3.setImageDrawable(boardCards[2].loadCardImage(context))
      binding.board4.setImageDrawable(boardCards[3].loadCardImage(context))
      binding.board5.setImageDrawable(boardCards[4].loadCardImage(context))
    }
  }
}