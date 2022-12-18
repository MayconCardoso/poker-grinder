package com.mctech.pokergrinder.grind.presentation.creation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mctech.pokergrinder.architecture.ViewCommand
import com.mctech.pokergrinder.architecture.extensions.bindCommand
import com.mctech.pokergrinder.architecture.extensions.bindState
import com.mctech.pokergrinder.architecture.extensions.onDataFormFilled
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.grind.presentation.creation.databinding.FragmentNewGrindBinding
import com.mctech.pokergrinder.grind.presentation.navigation.GrindNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
public class NewGrindFragment : Fragment(R.layout.fragment_new_grind) {

  // region Variables

  /**
   * New Grind View Model
   */
  private val viewModel by viewModels<NewGrindViewModel>()

  /**
   * New Grind Ui Binding
   */
  private val binding by viewBinding(FragmentNewGrindBinding::bind)

  /**
   * Feature navigation
   */
  @Inject
  public lateinit var navigation: GrindNavigation

  // endregion

  // region Lifecycle

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    // Setup Listeners
    setupListeners()

    // Observes state.
    bindState(viewModel.componentState, ::rendersState)

    // Observes commands
    bindCommand(viewModel, ::consumeCommand)
  }

  // endregion

  // region State Manipulation

  private fun rendersState(state: NewGrindState) {
    binding.sessionTitle.setText(state.suggestedTitle)
  }

  // endregion

  // region Component Setup

  private fun setupListeners() {
    binding.save.setOnClickListener {
      viewModel.interact(
        NewGrindInteraction.SaveGrind(
          title = binding.sessionTitle.text.toString(),
        )
      )
    }

    listOf(binding.sessionTitle).onDataFormFilled { allSet ->
      binding.save.isEnabled = allSet
    }
  }

  // endregion

  // region Commands

  private fun consumeCommand(command: ViewCommand) {
    when (command) {
      is NewGrindCommand.CloseScreen -> navigation.navigateBack()
    }
  }

  // endregion
}