package com.mctech.pokergrinder.design.extension

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Creates a full screen modifier.
 */
fun Modifier.fillScreen() = this
  .fillMaxWidth()
  .fillMaxHeight()
  .padding(all = 12.dp)