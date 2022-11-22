package com.mctech.pokergrinder.tournament.presentation.list_component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mctech.pokergrinder.architecture.utility.SimpleItemDiffCallback
import com.mctech.pokergrinder.tournament.presentation.list_component.databinding.FragmentTournamentListItemBinding
import com.mctech.pokergrinder.tournaments.domain.entities.Tournament

internal class TournamentListAdapter(
  private val tournamentClicked: (Tournament) -> Unit,
) : ListAdapter<Tournament, TournamentListAdapter.ViewHolder>(SimpleItemDiffCallback()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
    FragmentTournamentListItemBinding.inflate(
      LayoutInflater.from(parent.context), parent, false
    )
  )

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  inner class ViewHolder(
    private val binding: FragmentTournamentListItemBinding,
  ) : RecyclerView.ViewHolder(binding.root) {

    init {
      binding.root.setOnClickListener {
        onItemClicked(getItem(absoluteAdapterPosition))
      }
    }

    fun bind(tournament: Tournament) {
      binding.title.text = tournament.title
      binding.buyIn.text = tournament.formattedBuyIn()
    }

    private fun onItemClicked(item: Tournament) {
      tournamentClicked(item)
    }
  }
}