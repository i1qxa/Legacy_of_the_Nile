package com.Legacy.ofthe.Nile.ui.main.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import com.Legacy.ofthe.Nile.R
import com.Legacy.ofthe.Nile.databinding.FragmentStartGameBinding
import com.Legacy.ofthe.Nile.ui.exit.ExitFragment
import com.Legacy.ofthe.Nile.ui.main.chose_lvl.ChoseLvlFragment
import com.Legacy.ofthe.Nile.ui.main.rules.RulesFragment
import com.Legacy.ofthe.Nile.ui.main.settings.SettingsFragment


class StartGameFragment() : Fragment() {

    private val binding by lazy { FragmentStartGameBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBtnClickListeners()
    }


    private fun setupBtnClickListeners(){
        binding.btnPlay.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.mainConteiner, ChoseLvlFragment())
                addToBackStack(null)
                commit()
            }
        }
        binding.btnExit.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.mainConteiner, ExitFragment())
                addToBackStack(null)
                commit()
            }
        }
        binding.btnRules.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.mainConteiner, RulesFragment())
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