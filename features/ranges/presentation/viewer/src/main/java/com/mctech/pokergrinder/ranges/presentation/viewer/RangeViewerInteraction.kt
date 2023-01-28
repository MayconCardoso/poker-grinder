package com.mctech.pokergrinder.ranges.presentation.viewer

import com.mctech.pokergrinder.architecture.UserInteraction
import com.mctech.pokergrinder.ranges.domain.entities.Range

internal sealed class RangeViewerInteraction : UserInteraction {
  data class OnScreenFirstOpened(val range: Range) : RangeViewerInteraction()
}