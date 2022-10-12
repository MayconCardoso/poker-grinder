package com.mctech.pokergrinder.grind.presentation.pager_container

import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.presentation.flip_list.GrindDetailsGameplayFragment
import com.mctech.pokergrinder.grind.presentation.summary.GrindSummaryFragment
import com.mctech.pokergrinder.grind.presentation.tournament_list.GrindDetailsFragment

internal class GrindDetailContainerAdapter(
  fragmentManager: FragmentManager,
  lifecycle: Lifecycle,
  val session: Session,
) : FragmentStateAdapter(fragmentManager, lifecycle) {

  /**
   * Holds the current shown tabs on grind details feature.
   */
  private val discoverTabs by lazy { listOf(GrindTab.SUMMARY,
    GrindTab.TOURNAMENT,
    GrindTab.GAMEPLAY) }

  override fun getItemCount() = discoverTabs.size

  override fun createFragment(position: Int) = when (position) {
    // Creates summary fragment.
    GrindTab.SUMMARY.position -> GrindSummaryFragment()
      .apply {
      arguments = bundleOf(GrindSummaryFragment.SESSION_PARAM to session)
    }

    // Creates tournament fragment.
    GrindTab.TOURNAMENT.position -> GrindDetailsFragment()
      .apply {
      arguments = bundleOf(GrindDetailsFragment.SESSION_PARAM to session)
    }

    // Creates gameplay fragment
    GrindTab.GAMEPLAY.position -> GrindDetailsGameplayFragment()
      .apply {
      arguments = bundleOf(GrindDetailsGameplayFragment.SESSION_PARAM to session)
    }

    // Fragment not found.
    else -> error("Feature not supported on GrindDetailContainerAdapter")
  }

  enum class GrindTab(val position: Int, @StringRes val titleRes: Int) {
    SUMMARY(0, com.mctech.pokergrinder.localization.R.string.summary),
    TOURNAMENT(1, com.mctech.pokergrinder.localization.R.string.tournament),
    GAMEPLAY(2, com.mctech.pokergrinder.localization.R.string.gameplay),
    ;

    companion object {

      fun byPosition(position: Int): GrindTab {
        return values().first { it.position == position }
      }
    }
  }
}
