package com.mctech.pokergrinder.grind.presentation.pager_container

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.presentation.navigation.GrindNavigation
import com.mctech.pokergrinder.grind.presentation.pager_container.databinding.FragmentGrindDetailsContainerBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
public class GrindDetailContainerFragment : Fragment(R.layout.fragment_grind_details_container) {

  /**
   * New Grind Ui Binding
   */
  private val binding by viewBinding(FragmentGrindDetailsContainerBinding::bind)

  /**
   * Feature navigation
   */
  @Inject
  public lateinit var navigation: GrindNavigation

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    // Gets selected session.
    val session = arguments?.getSerializable(SESSION_PARAM) as Session
    binding.toolbar.title = session.title

    // Setup view pager with session.
    setupViewPager(session)

    // Setup tab layout.
    setupTabLayout()

    // Inflates menu
    setupToolbarMenu()
  }

  private fun setupViewPager(session: Session) = with(binding) {
    containerPager.adapter = GrindDetailContainerAdapter(childFragmentManager, lifecycle, session)
    containerPager.offscreenPageLimit = 3
    containerPager.isUserInputEnabled = false
  }

  private fun setupTabLayout() {
    TabLayoutMediator(binding.containerTab, binding.containerPager) { tab, position ->
      tab.text = getString(
        GrindDetailContainerAdapter.GrindTab.byPosition(position).titleRes
      )
    }.attach()
  }

  private fun setupToolbarMenu() {
    binding.toolbar.inflateMenu(R.menu.session_menu)
    binding.toolbar.setOnMenuItemClickListener {
      if (it.itemId == R.id.settings_fragment) {
        navigation.goToSettings()
      }
      true
    }
  }


  // region Builder

  public companion object {
    public const val SESSION_PARAM: String = "SESSION_PARAM"
  }

  // endregion
}