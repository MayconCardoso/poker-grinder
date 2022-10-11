package com.mctech.pokergrinder.deck.components.card_picker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mctech.pokergrinder.architecture.utility.SimpleItemDiffCallback
import com.mctech.pokergrinder.deck.components.card_picker.databinding.FragmentCardPickerItemBinding

internal class CardPickerAdapter :
  ListAdapter<CardPickerState, CardPickerAdapter.ViewHolder>(SimpleItemDiffCallback()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
    FragmentCardPickerItemBinding.inflate(
      LayoutInflater.from(parent.context), parent, false
    )
  )

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  inner class ViewHolder(
    private val binding: FragmentCardPickerItemBinding,
  ) : RecyclerView.ViewHolder(binding.root) {

    fun bind(cardState: CardPickerState) {
      binding.card.setImageDrawable(cardState.card.loadCardImage(binding.root.context))
      binding.card.alpha = if (cardState.disabled) 0.5F else 1F
    }
  }

}