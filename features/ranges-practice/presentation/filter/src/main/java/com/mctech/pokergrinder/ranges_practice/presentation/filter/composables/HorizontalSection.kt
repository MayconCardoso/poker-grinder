package com.mctech.pokergrinder.ranges_practice.presentation.filter.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mctech.pokergrinder.design.compose.typoProvider
import com.mctech.pokergrinder.localization.R
import com.mctech.pokergrinder.ranges_practice.presentation.filter.RangeFilterOption

@Composable
internal fun HorizontalSection(
  title: String,
  options: List<RangeFilterOption>,
  clickedOption: (RangeFilterOption) -> Unit
) {
  // Initial space
  Spacer(modifier = Modifier.height(12.dp))

  // Option title
  Text(
    text = title,
    style = TextStyle(
      fontSize = 18.sp,
      fontWeight = FontWeight.Bold,
    ),
    modifier = Modifier.padding(start = 12.dp, end = 12.dp),
  )

  // Render items
  LazyRow(
    modifier = Modifier.padding(start = 12.dp, end = 12.dp),
  ) {
    items(options) { option ->
      if (option.isSelected) {
        Button(onClick = { clickedOption(option) }) {
          OptionText(option)
        }
      } else {
        OutlinedButton(onClick = { clickedOption(option) }) {
          OptionText(option)
        }
      }
      Spacer(modifier = Modifier.width(8.dp))
    }
  }
}

@Composable
internal fun OptionText(option: RangeFilterOption) {
  Text(
    text = option.name ?: stringResource(id = R.string.all),
    style = typoProvider().button
  )
}