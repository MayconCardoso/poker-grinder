package com.mctech.pokergrinder.grind.presentation.creation

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.mctech.pokergrinder.architecture.ViewCommand
import com.mctech.pokergrinder.architecture.extensions.bindCommand
import com.mctech.pokergrinder.architecture.extensions.bindState
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.grind.presentation.databinding.ActivityNewGrindBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
public class NewGrindActivity : AppCompatActivity() {

  // region Variables

  /**
   * New Grind View Model
   */
  private val viewModel by viewModels<NewGrindViewModel>()

  /**
   * New Grind Ui Binding
   */
  private val binding by viewBinding(ActivityNewGrindBinding::inflate)

  // endregion

  // region Lifecycle

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

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
  }

  // endregion

  // region Commands

  private fun consumeCommand(command: ViewCommand) {
    when (command) {
      is NewGrindCommand.CloseScreen -> finish()
    }
  }

  // endregion

  // region Builder

  internal companion object {
    fun navigate(origin: FragmentActivity) {
      origin.startActivity(Intent(origin, NewGrindActivity::class.java))
    }
  }

  // endregion
}