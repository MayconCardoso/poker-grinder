package com.mctech.pokergrinder.architecture.extensions

import android.view.View

fun measureDimension(desiredSize: Int, measureSpec: Int): Int {
  val specMode = View.MeasureSpec.getMode(measureSpec)
  val specSize = View.MeasureSpec.getSize(measureSpec)

  return when (specMode) {
    View.MeasureSpec.EXACTLY -> specSize
    View.MeasureSpec.AT_MOST -> desiredSize.coerceAtMost(specSize)
    else -> desiredSize
  }
}