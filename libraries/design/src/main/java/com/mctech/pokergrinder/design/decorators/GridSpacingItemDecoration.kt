package com.mctech.pokergrinder.design.decorators

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import java.util.SortedSet

/**
 * Useful decoration for static recycler view. If you are going to use adding / removing
 * or another kind of animation please consider another solution like setting margins / paddings
 * directly to list items.
 *
 * TL;DR: After adding / removing or changing span count we can get unexpected spacing behaviour.
 * For more details please check this answer on SO - https://stackoverflow.com/a/30701422
 */
class GridSpacingItemDecoration(
  private val spanCount: Int,
  private val spacing: Int,
  private val includeEdge: Boolean = false,
  private val offGridPositions: SortedSet<Int> = sortedSetOf(),
) : RecyclerView.ItemDecoration() {

  override fun getItemOffsets(
    outRect: Rect,
    view: View,
    parent: RecyclerView,
    state: RecyclerView.State,
  ) {
    val adapterPosition = parent.getChildAdapterPosition(view)
      .takeIf { it !in offGridPositions } ?: return

    val offset = offGridPositions.headSet(adapterPosition).size
    val position = adapterPosition - offset
    val column = position % spanCount

    if (includeEdge) {
      outRect.left = spacing - column * spacing / spanCount
      outRect.right = (column + 1) * spacing / spanCount

      if (position < spanCount) {
        outRect.top = spacing
      }
      outRect.bottom = spacing
    } else {
      outRect.left = column * spacing / spanCount
      outRect.right = spacing - (column + 1) * spacing / spanCount
      if (position >= spanCount) {
        outRect.top = spacing
      }
    }
  }
}
