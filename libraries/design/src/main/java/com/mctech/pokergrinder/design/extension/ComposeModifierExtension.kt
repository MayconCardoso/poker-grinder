package com.mctech.pokergrinder.design.extension

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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

/**
 * Creates a click event without the ripple effect.
 */
fun Modifier.clickableWithoutRipple(
  interactionSource: MutableInteractionSource = MutableInteractionSource(),
  onClick: () -> Unit
) = this.then(
  Modifier.clickable(
    interactionSource = interactionSource,
    indication = null,
    onClick = { onClick() }
  )
)