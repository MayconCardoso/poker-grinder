package com.mctech.pokergrinder.ranges.presentation.viewer.component

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mctech.pokergrinder.architecture.utility.SimpleItemDiffCallback
import com.mctech.pokergrinder.ranges.domain.entities.RangeHandInput
import com.mctech.pokergrinder.ranges.presentation.viewer.databinding.FragmentRangeViewerItemBinding

class RangeViewerComponentAdapter :
  ListAdapter<RangeHandInput, RangeViewerComponentAdapter.ViewHolder>(SimpleItemDiffCallback()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
    FragmentRangeViewerItemBinding.inflate(
      LayoutInflater.from(parent.context), parent, false
    )
  )

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  inner class ViewHolder(
    private val binding: FragmentRangeViewerItemBinding,
  ) : RecyclerView.ViewHolder(binding.root) {

    fun bind(state: RangeHandInput) {
      // Sets title
      binding.handTitle.text = state.hand.formattedName()

      //Sets background
      binding.handShape.setBackgroundColor(Color.parseColor(state.filledColor))
    }
  }
}
