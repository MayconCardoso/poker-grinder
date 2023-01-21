package com.mctech.pokergrinder.summary.presentation.tournaments.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.mctech.pokergrinder.design.R
import com.mctech.pokergrinder.summary.domain.entities.TournamentSummary
import com.mctech.pokergrinder.summary.presentation.tournament.databinding.SummaryTournamentComponentBinding

/**
 * Represents a summary component.
 */
internal class SummaryTournamentComponent @JvmOverloads constructor(
  context: Context, attrs: AttributeSet? = null,
) : FrameLayout(context, attrs) {

  init {
    layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
  }

  private val binding = SummaryTournamentComponentBinding.inflate(
    LayoutInflater.from(context), this, true
  )

  fun render(tournament: TournamentSummary) {
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