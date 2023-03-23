package com.mctech.chart.money

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.mctech.pokergrinder.architecture.extensions.measureDimension
import com.mctech.pokergrinder.architecture.extensions.pixel

public class ChartMoneyVariationView @JvmOverloads constructor(
  context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr), ChartMoneyVariationPainterCallback {

  /**
   * Used to hold and compute data size, etc.
   */
  private val dataController by lazy { ChartMoneyVariationController() }

  /**
   * Used to show preview mode.
   */
  private val editMode by lazy { ChartMoneyVariationEditMode() }

  /**
   * Used to manage all painters.
   */
  private val painter by lazy { ChartMoneyVariationPainter(context = context, callback = this) }

  /**
   * Used to draw on the view itself on canvas.
   */
  private val drawer by lazy { ChartMoneyVariationDrawer() }

  /**
   * Suggested height if it is not specified.
   */
  private val suggestionHeight by lazy {
    100F.pixel().toInt()
  }

  // Start edit mode preview.
  init {
    editMode.attach(this)
  }

  public fun render(data: List<MoneyVariationEntry>) {
    if (data.isEmpty()) return
    dataController.setData(data)
    dataController.updateItemsDimensions(width, height)
    requestLayout()
    invalidate()
  }

  override fun getSuggestedMinimumHeight(): Int {
    return suggestionHeight
  }

  override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    dataController.updateItemsDimensions(width, height)
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    val desiredWidth = suggestedMinimumWidth + paddingLeft + paddingRight
    val desiredHeight = suggestedMinimumHeight + paddingTop + paddingBottom

    setMeasuredDimension(
      measureDimension(desiredWidth, widthMeasureSpec),
      measureDimension(desiredHeight, heightMeasureSpec)
    )
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)
    drawer.prepare(
      centerZeroY = dataController.centerZeroY,
      elementSize = dataController.distanceBetweenElements,
      data = dataController.dataElements
    )
    drawer.draw(canvas, painter)
  }
}