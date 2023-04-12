package com.mctech.pokergrinder.summary.presentation.pager_container

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.summary.presentation.pager_container.databinding.FragmentSummaryContainerBinding

public class SummaryContainerFragment : Fragment(R.layout.fragment_summary_container) {

  private val binding by viewBinding(FragmentSummaryContainerBinding::bind)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupViewPager()
    setupTabLayout()
  }

  private fun setupViewPager() = with(binding) {
    containerPager.adapter = SummaryContainerAdapter(childFragmentManager, lifecycle)
    containerPager.offscreenPageLimit = 3
  }

  private fun setupTabLayout() {
    TabLayoutMediator(binding.containerTab, binding.containerPager) { tab, position ->
      tab.text = getString(
        SummaryContainerAdapter.SummaryTab.byPosition(position).titleRes
      )
    }.attach()
  }
}