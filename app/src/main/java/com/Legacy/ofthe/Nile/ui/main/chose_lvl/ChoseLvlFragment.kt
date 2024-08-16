package com.Legacy.ofthe.Nile.ui.main.chose_lvl

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.Legacy.ofthe.Nile.R
import com.Legacy.ofthe.Nile.databinding.FragmentChoseLvlBinding
import com.Legacy.ofthe.Nile.ui.main.game.easy.GameEasyFragment
import com.Legacy.ofthe.Nile.ui.main.game.hard.GameHardFragment
import com.Legacy.ofthe.Nile.ui.main.game.middle.GameMiddleFragment
import com.Legacy.ofthe.Nile.ui.main.start.StartGameFragment


class ChoseLvlFragment : Fragment() {

    private val binding by lazy { FragmentChoseLvlBinding.inflate(layoutInflater) }
    private var selectedDifficulty = 1

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
        binding.btnEasy.setOnClickListener {
            clearSelectedLvl()
            binding.btnEasy.setBackgroundResource(R.drawable.btn_red_bckgr)
            selectedDifficulty = 1
        }
        binding.btnMiddle.setOnClickListener {
            clearSelectedLvl()
            binding.btnMiddle.setBackgroundResource(R.drawable.btn_red_bckgr)
            selectedDifficulty = 2
        }
        binding.btnHard.setOnClickListener {
            clearSelectedLvl()
            binding.btnHard.setBackgroundResource(R.drawable.btn_red_bckgr)
            selectedDifficulty = 3
        }
        binding.btnChoseLvl.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.mainConteiner, StartGameFragment())
                addToBackStack(null)
                commit()
            }
        }
        binding.btnStart.setOnClickListener {
            val targetFragment = when(selectedDifficulty){
                2 -> GameMiddleFragment()
                3 -> GameHardFragment()
                else -> GameEasyFragment()
            }
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.mainConteiner, targetFragment)
                addToBackStack(null)
                commit()
            }
        }
    }

    private fun clearSelectedLvl(){
        with(binding){
            btnEasy.setBackgroundResource(R.drawable.btn_yellow_bckgr)
            btnMiddle.setBackgroundResource(R.drawable.btn_yellow_bckgr)
            btnHard.setBackgroundResource(R.drawable.btn_yellow_bckgr)
        }
    }

}