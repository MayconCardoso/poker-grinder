package com.mctech.pokergrinder.summary.presentation.tournaments.list

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mctech.pokergrinder.architecture.utility.SimpleItemDiffCallback
import com.mctech.pokergrinder.summary.domain.entities.TournamentSummary
import com.mctech.pokergrinder.summary.presentation.tournaments.component.SummaryTournamentComponent

internal class SummaryTournamentListAdapter(
  val onTournamentClick: (tournament: TournamentSummary) -> Unit,
) : ListAdapter<TournamentSummary, SummaryTournamentListAdapter.ViewHolder>(
  SimpleItemDiffCallback()
) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
    SummaryTournamentComponent(parent.context)
  )

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  inner class ViewHolder(
    private val component: SummaryTournamentComponent,
  ) : RecyclerView.ViewHolder(component) {

    init {
      component.setOnClickListener {
        onTournamentClick(getItem(absoluteAdapterPosition))
      }
    }

    fun bind(tournament: TournamentSummary) {
      component.render(tournament)
    }
  }

}