package com.mctech.pokergrinder.grind.presentation.grind_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mctech.pokergrinder.architecture.utility.SimpleItemDiffCallback
import com.mctech.pokergrinder.design.R
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.presentation.databinding.FragmentGrindItemBinding

internal class GrindAdapter(
  private val eventConsumer: GrindAdapterConsumer,
) :
  ListAdapter<Session, GrindAdapter.ViewHolder>(SimpleItemDiffCallback()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
    FragmentGrindItemBinding.inflate(
      LayoutInflater.from(parent.context), parent, false
    )
  )

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  inner class ViewHolder(
    private val binding: FragmentGrindItemBinding,
  ) : RecyclerView.ViewHolder(binding.root) {

    init {
      binding.root.setOnClickListener {
        onItemClicked(getItem(absoluteAdapterPosition))
      }
    }


    fun bind(session: Session) {
      val context = binding.root.context

      binding.title.text = session.title
      binding.amount.text = session.formattedAmount()
      binding.countBuyIn.text = context.getString(
        com.mctech.pokergrinder.localization.R.string.count_buy_in,
        session.countBuyIn,
      )

      // Change indicator color
      val color = ContextCompat.getColor(
        binding.root.context,
        if (session.outcome >= 0) R.color.deposit else R.color.withdraw
      )
      binding.indicator.setBackgroundColor(color)
    }
  }

  private fun onItemClicked(item: Session) {
    eventConsumer.consume(GrindAdapterConsumerEvent.SessionClicked(item))
  }
}