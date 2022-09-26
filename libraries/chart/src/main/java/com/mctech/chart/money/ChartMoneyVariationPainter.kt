package com.mctech.chart.money

import android.content.Context
import android.graphics.Paint
import androidx.core.content.ContextCompat
import com.mctech.pokergrinder.architecture.extensions.dp

internal class ChartMoneyVariationPainter(
  private val context: Context,
  private val callback: ChartMoneyVariationPainterCallback,
) {
  // View aspect size
  private val variationLineSize by lazy { 2.dp().toFloat() }

  // Painters
  internal val positivePaint by lazy { defaultLinePaint(positiveColor) }
  internal val negativePaint by lazy { defaultLinePaint(negativeColor) }
  internal val centerPainter by lazy {
    defaultLinePaint(com.mctech.pokergrinder.design.R.color.colorCenterChartLine).apply {
      strokeWidth = 1.dp().toFloat()
    }
  }

  private var positiveColor: Int = com.mctech.pokergrinder.design.R.color.deposit
    set(value) {
      field = value
      updatePaintColor(positivePaint, value)
    }
  private var negativeColor: Int = com.mctech.pokergrinder.design.R.color.withdraw
    set(value) {
      field = value
      updatePaintColor(negativePaint, value)
    }

  private fun defaultLinePaint(colorInt: Int) = Paint().apply {
    isAntiAlias = true
    style = Paint.Style.FILL
    strokeWidth = variationLineSize
    color = ContextCompat.getColor(context, colorInt)
  }

  private fun updatePaintColor(paint: Paint, colorInt: Int) {
    paint.color = ContextCompat.getColor(context, colorInt)
    callback.invalidate()
  }
}

internal interface ChartMoneyVariationPainterCallback {
  fun invalidate()
}

