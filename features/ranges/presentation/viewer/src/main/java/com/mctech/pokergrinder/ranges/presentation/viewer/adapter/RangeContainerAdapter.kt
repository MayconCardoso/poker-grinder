package com.mctech.pokergrinder.ranges.presentation.viewer.adapter

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mctech.pokergrinder.ranges.domain.entities.Range
import com.mctech.pokergrinder.ranges.presentation.viewer.page.RangeViewerPageFragment

internal class RangeContainerAdapter(
  fragmentManager: FragmentManager,
  lifecycle: Lifecycle,
) : FragmentStateAdapter(fragmentManager, lifecycle) {

  /**
   * Holds the current rendered range.
   */
  private var range: Range? = null

  override fun getItemCount() = range?.handPosition?.size ?: 0

  override fun createFragment(position: Int) = RangeViewerPageFragment(
    range?.handPosition?.get(position) ?: error("Position not found.")
  )

  fun renderPages(range: Range) {
    this.range = range
    notifyDataSetChanged()
  }

  fun getTitleByPosition(position: Int) = range?.handPosition
    ?.getOrNull(position)
    ?.position?.name.orEmpty()

}
