package com.Legacy.ofthe.Nile.ui.main.game.easy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.Legacy.ofthe.Nile.R
import com.Legacy.ofthe.Nile.databinding.FragmentGameEasyBinding
import com.Legacy.ofthe.Nile.ui.loading.LoadingViewModel
import com.Legacy.ofthe.Nile.ui.main.settings.SettingsFragment
import com.Legacy.ofthe.Nile.ui.main.start.StartGameFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameEasyFragment : Fragment() {

    private val binding by lazy { FragmentGameEasyBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<EasyViewModel>()
    private val listOfGameItemViews by lazy { listOf(binding.item1, binding.item2, binding.item3) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            delay(200)
            viewModel.setupGameItemImgResources(listOfGameItemViews)
            setupBtnClickListeners()
        }

    }

    private fun setupBtnClickListeners(){
        binding.btnMix.setOnClickListener {
            viewModel.mixGameItems()
        }
        binding.btnToStart.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.mainConteiner, StartGameFragment())
                addToBackStack(null)
                commit()
            }
        }
        binding.btnSettings.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.mainConteiner, SettingsFragment())
                addToBackStack(null)
                commit()
            }
        }
    }

}