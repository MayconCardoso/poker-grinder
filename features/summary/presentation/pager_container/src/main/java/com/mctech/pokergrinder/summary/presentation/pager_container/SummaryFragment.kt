package com.mctech.pokergrinder.summary.presentation.pager_container

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.summary.presentation.navigation.SummaryNavigation
import com.mctech.pokergrinder.summary.presentation.pager_container.databinding.FragmentSummaryBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SummaryFragment : Fragment(R.layout.fragment_summary) {

  /**
   * Feature Ui Binding
   */
  private val binding by viewBinding(FragmentSummaryBinding::bind)

  /**
   * Feature navigation
   */
  @Inject
  lateinit var navigation: SummaryNavigation

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.developer.setOnClickListener {
      navigation.goToSupportDeveloper()
    }
  }
}