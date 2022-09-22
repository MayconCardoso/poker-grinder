package com.mctech.pokergrinder.bankroll.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mctech.pokergrinder.architecture.utility.SimpleItemDiffCallback
import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransaction
import com.mctech.pokergrinder.bankroll.presentation.databinding.FragmentBankrollItemBinding
import com.mctech.pokergrinder.design.R

internal class BankrollAdapter :
  ListAdapter<BankrollTransaction, BankrollAdapter.ViewHolder>(SimpleItemDiffCallback()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
    FragmentBankrollItemBinding.inflate(
      LayoutInflater.from(parent.context), parent, false
    )
  )

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  inner class ViewHolder(
    private val binding: FragmentBankrollItemBinding,
  ) : RecyclerView.ViewHolder(binding.root) {

    fun bind(transaction: BankrollTransaction) {
      binding.title.text = transaction.description
      binding.date.text = transaction.formattedDate()
      binding.amount.text = transaction.formattedAmount()

      // Change indicator color
      val color = ContextCompat.getColor(
        binding.root.context,
        if (transaction.amount >= 0) R.color.deposit else R.color.withdraw
      )
      binding.indicator.setBackgroundColor(color)
    }
  }
}