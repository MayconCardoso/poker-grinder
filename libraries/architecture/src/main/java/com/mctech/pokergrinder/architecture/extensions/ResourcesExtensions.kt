package com.mctech.pokergrinder.architecture.extensions

import android.content.res.Resources

fun Int.dp() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Float.dp() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Int.pixel(): Float {
  val density = Resources.getSystem().displayMetrics.density
  return this / density
}

fun Float.pixel(): Float {
  val density = Resources.getSystem().displayMetrics.density
  return this / density
}