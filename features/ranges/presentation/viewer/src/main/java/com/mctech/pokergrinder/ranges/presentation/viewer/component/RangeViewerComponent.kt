package com.mctech.pokergrinder.ranges.presentation.viewer.component

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mctech.pokergrinder.architecture.extensions.dp
import com.mctech.pokergrinder.design.decorators.GridSpacingItemDecoration
import com.mctech.pokergrinder.ranges.domain.entities.EmptyRange
import com.mctech.pokergrinder.ranges.domain.entities.RangeHandInput

class RangeViewerComponent @JvmOverloads constructor(
  context: Context, attrs: AttributeSet? = null,
) : FrameLayout(context, attrs) {

  private val rangeRecyclerView = RecyclerView(context)
  private val rangeRecyclerViewAdapter = RangeViewerComponentAdapter()

  init {
    setupRecyclerView()
    setupEditMode()
  }

  private fun setupRecyclerView() {
    // Setup view
    rangeRecyclerView.adapter = rangeRecyclerViewAdapter
    rangeRecyclerView.layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
    rangeRecyclerView.layoutManager = GridLayoutManager(context, 13)
    rangeRecyclerView.addItemDecoration(GridSpacingItemDecoration(13, 2.dp()))

    // Add on container
    addView(rangeRecyclerView)
  }

  private fun setupEditMode() {
    if (isInEditMode) {
      render(EmptyRange.rangeHands.map { RangeHandInput(hand = it) })
    }
  }

  fun render(inputs: List<RangeHandInput>) {
    rangeRecyclerViewAdapter.submitList(inputs)
  }

}