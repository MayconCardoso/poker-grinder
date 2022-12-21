package com.mctech.pokergrinder.settings.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.architecture.extensions.avoidFrozenFrames
import com.mctech.pokergrinder.architecture.extensions.bindState
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.settings.domain.entities.Settings
import com.mctech.pokergrinder.settings.domain.entities.SettingsAvailable
import com.mctech.pokergrinder.settings.domain.entities.isEnabled
import com.mctech.pokergrinder.settings.presentation.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
public class SettingsFragment : Fragment(R.layout.fragment_settings) {

  // region Variables

  /**
   * Feature View Model
   */
  private val viewModel by viewModels<SettingsViewModel>()

  /**
   * Feature Ui Binding
   */
  private val binding by viewBinding(FragmentSettingsBinding::bind)

  // endregion

  // region Lifecycle

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    avoidFrozenFrames {
      // Initialize view model
      viewModel.initialize()

      // Observers state changes
      bindState(viewModel.componentState, ::consumeState)

      // Setup listeners
      setupListeners()
    }
  }

  // endregion

  // region State Manipulation

  private fun consumeState(state: ComponentState<List<Settings>>) {
    when (state) {
      is ComponentState.Error -> rendersError()
      is ComponentState.Loading -> rendersLoading()
      is ComponentState.Success -> rendersSuccess(state.result)
    }
  }

  private fun rendersLoading() {
    binding.progress.isVisible = true
    binding.groupSuccess.isVisible = false
  }

  private fun rendersSuccess(state: List<Settings>) {
    // Show containers.
    binding.progress.isVisible = false
    binding.groupSuccess.isVisible = state.isNotEmpty()

    // Render settings
    binding.cbGroup.isChecked = state.isEnabled(SettingsAvailable.GROUP_TOURNAMENTS)
  }

  private fun rendersError() {
    Log.i("TournamentsFragment", "Error while loading screen.")
  }

  // endregion

  // region Component setup

  private fun setupListeners() {
    binding.cbGroup.setOnCheckedChangeListener { _, isChecked ->
      viewModel.interact(
        SettingsInteraction.OnSettingChanged(
          setting = SettingsAvailable.GROUP_TOURNAMENTS,
          value = isChecked,
        )
      )
    }
  }

  // endregion

}