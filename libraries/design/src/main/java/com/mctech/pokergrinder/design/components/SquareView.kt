package com.mctech.pokergrinder.design.components

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * This view will compute its height with same value of width to make a perfect square.
 * It is used on grid views where elements must be squared and the width is computed dynamically.
 */
class SquareView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, widthMeasureSpec)
  }
}
