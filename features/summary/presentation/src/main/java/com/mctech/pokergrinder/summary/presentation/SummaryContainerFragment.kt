package com.mctech.pokergrinder.summary.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.summary.presentation.databinding.FragmentSummaryContainerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
public class SummaryContainerFragment : Fragment(R.layout.fragment_summary_container) {

  private val binding by viewBinding(FragmentSummaryContainerBinding::bind)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupViewPager()
    setupTabLayout()
  }

  private fun setupViewPager() = with(binding) {
    containerPager.adapter = SummaryContainerContainerAdapter(childFragmentManager, lifecycle)
    containerPager.offscreenPageLimit = 2
    containerPager.isUserInputEnabled = false
  }

  private fun setupTabLayout() {
    TabLayoutMediator(binding.containerTab, binding.containerPager) { tab, position ->
      tab.text = getString(
        SummaryContainerContainerAdapter.SummaryTab.byPosition(position).titleRes
      )
    }.attach()
  }
}