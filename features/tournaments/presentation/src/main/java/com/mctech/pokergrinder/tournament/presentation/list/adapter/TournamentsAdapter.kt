package com.mctech.pokergrinder.tournament.presentation.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mctech.pokergrinder.architecture.utility.SimpleItemDiffCallback
import com.mctech.pokergrinder.tournament.presentation.databinding.FragmentTournamentsItemBinding
import com.mctech.pokergrinder.tournaments.domain.entities.Tournament

internal class TournamentsAdapter(
  private val eventConsumer: TournamentsAdapterConsumer,
) : ListAdapter<Tournament, TournamentsAdapter.ViewHolder>(SimpleItemDiffCallback()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
    FragmentTournamentsItemBinding.inflate(
      LayoutInflater.from(parent.context), parent, false
    )
  )

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  inner class ViewHolder(
    private val binding: FragmentTournamentsItemBinding,
  ) : RecyclerView.ViewHolder(binding.root) {

    init {
      binding.root.setOnClickListener {
        onItemClicked(getItem(absoluteAdapterPosition))
      }
    }

    fun bind(tournament: Tournament) {
      binding.title.text = tournament.title
      binding.buyIn.text = tournament.formattedBuyIn()
      binding.gtd.text = tournament.formattedGuaranteed()
      binding.startsAt.text = tournament.formattedTime()
    }

    private fun onItemClicked(item: Tournament) {
      eventConsumer.consume(TournamentsAdapterConsumerEvent.TournamentClicked(item))
    }
  }
}