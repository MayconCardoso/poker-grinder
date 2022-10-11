package com.mctech.pokergrinder.summary.presentation

import androidx.annotation.StringRes
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mctech.pokergrinder.summary.tournaments.SummaryTournamentListFragment

internal class SummaryContainerAdapter(
  fragmentManager: FragmentManager,
  lifecycle: Lifecycle,
) : FragmentStateAdapter(fragmentManager, lifecycle) {

  /**
   * Holds the current shown tabs on home summary feature.
   */
  private val discoverTabs by lazy { listOf(SummaryTab.PERFORMANCE, SummaryTab.TOURNAMENT) }

  override fun getItemCount() = discoverTabs.size

  override fun createFragment(position: Int) = when (position) {
    SummaryTab.PERFORMANCE.position -> SummaryFragment()
    SummaryTab.TOURNAMENT.position -> SummaryTournamentListFragment()
    else -> error("Feature not supported on SummaryContainerAdapter")
  }

  enum class SummaryTab(val position: Int, @StringRes val titleRes: Int) {
    PERFORMANCE(0, com.mctech.pokergrinder.localization.R.string.summary),
    TOURNAMENT(1, com.mctech.pokergrinder.localization.R.string.tournament),
    ;

    companion object {

      fun byPosition(position: Int): SummaryTab {
        return values().first { it.position == position }
      }
    }
  }
}
