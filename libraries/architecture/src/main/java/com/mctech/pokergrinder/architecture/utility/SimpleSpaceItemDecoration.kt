package com.mctech.pokergrinder.architecture.utility

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SimpleSpaceItemDecoration(
  private val leftOffset: Int = 0,
  private val rightOffset: Int = 0,
  private val topOffset: Int = 0,
  private val bottomOffset: Int = 0,
  private val excludeLast: Boolean = true
) : RecyclerView.ItemDecoration() {

  override fun getItemOffsets(
    outRect: Rect,
    view: View,
    parent: RecyclerView,
    state: RecyclerView.State
  ) = with(outRect) {
    parent.adapter?.let { adapter ->
      if (!(excludeLast && parent.getChildAdapterPosition(view) == adapter.itemCount - 1)) {
        left = leftOffset
        right = rightOffset
        top = topOffset
        bottom = bottomOffset
      }
    } ?: throw NullPointerException("Please set RecyclerView's adapter")
  }
}
