package com.mctech.pokergrinder.design.compose

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object TypographyFactory {
  @Composable
  operator fun invoke(colors: PokerGrinderColors) = Typography(
    h1 = TextStyle(
      fontWeight = FontWeight.SemiBold,
      fontSize = 30.sp,
      letterSpacing = 0.sp
    ),
    h2 = TextStyle(
      fontWeight = FontWeight.SemiBold,
      fontSize = 24.sp,
      letterSpacing = 0.5.sp
    ),
    caption = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = 14.sp,
    ),
    body1 = TextStyle(
      color = colors.textColorSecondary,
      fontWeight = FontWeight.Normal,
      fontSize = 12.sp,
    ),
    body2 = TextStyle(
      color = colors.textColorSecondary,
      fontWeight = FontWeight.Normal,
      fontSize = 14.sp,
    ),
  )
}
