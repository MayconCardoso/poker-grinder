package com.mctech.pokergrinder.grind.presentation

import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.presentation.grind_details.GrindDetailsFragment
import com.mctech.pokergrinder.grind.presentation.grind_gameplay.GrindDetailsGameplayFragment

internal class GrindDetailContainerAdapter(
  fragmentManager: FragmentManager,
  lifecycle: Lifecycle,
  val session: Session,
) : FragmentStateAdapter(fragmentManager, lifecycle) {

  /**
   * Holds the current shown tabs on grind details feature.
   */
  private val discoverTabs by lazy { listOf(GrindTab.TOURNAMENT, GrindTab.GAMEPLAY) }

  override fun getItemCount() = discoverTabs.size

  override fun createFragment(position: Int) = when (position) {
    // Creates tournament fragment.
    GrindTab.TOURNAMENT.position -> GrindDetailsFragment().apply {
      arguments = bundleOf(GrindDetailsFragment.SESSION_PARAM to session)
    }

    // Creates gameplay fragment
    GrindTab.GAMEPLAY.position -> GrindDetailsGameplayFragment().apply {
      arguments = bundleOf(GrindDetailsFragment.SESSION_PARAM to session)
    }

    // Fragment not found.
    else -> error("Feature not supported on GrindDetailContainerAdapter")
  }

  enum class GrindTab(val position: Int, @StringRes val titleRes: Int) {
    TOURNAMENT(0, com.mctech.pokergrinder.localization.R.string.tournament),
    GAMEPLAY(1, com.mctech.pokergrinder.localization.R.string.gameplay),
    ;

    companion object {

      fun byPosition(position: Int): GrindTab {
        return values().first { it.position == position }
      }
    }
  }
}
