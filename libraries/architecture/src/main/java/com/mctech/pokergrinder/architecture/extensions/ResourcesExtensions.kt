package com.mctech.pokergrinder.architecture.extensions

import android.content.res.Resources

fun Int.dp() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Float.dp() = (this * Resources.getSystem().displayMetrics.density).toInt()