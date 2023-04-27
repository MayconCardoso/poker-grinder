package com.mctech.pokergrinder.design.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mctech.pokergrinder.design.compose.PokerGrinder
import com.mctech.pokergrinder.design.compose.colorProvider

@Composable
fun HeaderToolbarUi(
  modifier: Modifier = Modifier,
  title: String,
  subtitle: String? = null,
  iconContent: @Composable BoxScope.() -> Unit = {}
) {
  // Draws the outer card.
  Box(
    modifier = modifier
      .background(PokerGrinder.LocalColors.current.primary)
      .fillMaxWidth()
      .padding(start = 15.dp, end = 12.dp)
      .height(60.dp)
  ) {
    // Draws the outer card.
    Column(modifier = Modifier.align(Alignment.CenterStart), verticalArrangement = Arrangement.Center) {
      // Draws the title
      Text(
        text = title,
        style = TextStyle(
          color = PokerGrinder.LocalColors.current.textColorInverted,
          fontSize = 19.sp,
          letterSpacing = 0.sp,
          fontWeight = FontWeight.SemiBold,
        ),
      )

      // Draws the Balance
      if (subtitle != null) {
        Text(
          text = subtitle,
          style = TextStyle(
            color = PokerGrinder.LocalColors.current.textColorInverted,
            fontSize = 16.sp,
            letterSpacing = 0.sp,
          ),
        )
      }
    }

    // Draws icons.
    iconContent()
  }
}

@Composable
fun ToolbarCounterIcon(
  modifier: Modifier = Modifier,
  icon: ImageVector,
  counterValue: Int = 0,
) {
  Box(
    modifier = modifier
      .height(34.dp)
      .width(34.dp),
    contentAlignment = Alignment.Center,
  ) {
    Icon(
      imageVector = icon,
      tint = MaterialTheme.colors.onPrimary,
      contentDescription = null,
      modifier = Modifier
        .size(28.dp)
        .clip(MaterialTheme.shapes.medium),
    )
    if (counterValue > 0) {
      Text(
        text = counterValue.toString(),
        textAlign = TextAlign.Center,
        style = TextStyle(
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = colorProvider().textColorInverted
        ),
        modifier = Modifier
          .width(16.dp)
          .height(16.dp)
          .align(Alignment.TopEnd)
          .background(
            color = colorProvider().primary,
            shape = CircleShape
          )
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
internal fun HeaderToolbarUiSingleTitlePreview() {
  PokerGrinder.PokerGrinderTheme {
    HeaderToolbarUi(title = "Bankroll")
  }
}

@Preview(showBackground = true)
@Composable
internal fun HeaderToolbarUiSingleTitleAndSubtitlePreview() {
  PokerGrinder.PokerGrinderTheme {
    HeaderToolbarUi(title = "Bankroll", subtitle = "$1000")
  }
}

@Preview(showBackground = true)
@Composable
internal fun HeaderToolbarUiSingleTitleAndIconPreview() {
  PokerGrinder.PokerGrinderTheme {
    HeaderToolbarUi(
      title = "Hello",
      iconContent = {
        ToolbarCounterIcon(
          icon = Icons.Filled.Settings,
          counterValue = 2,
          modifier = Modifier.align(Alignment.CenterEnd)
        )
      }
    )
  }
}