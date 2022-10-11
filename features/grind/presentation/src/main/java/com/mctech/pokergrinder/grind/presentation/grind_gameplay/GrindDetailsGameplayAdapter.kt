package com.mctech.pokergrinder.grind.presentation.grind_gameplay

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mctech.pokergrinder.architecture.utility.SimpleItemDiffCallback
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
      binding.title.text = item.tournament

      // Change indicator color
      val color = ContextCompat.getColor(
        binding.root.context,
        if (item.won) R.color.deposit else R.color.withdraw
      )
      binding.indicator.setBackgroundColor(color)
    }
  }
}