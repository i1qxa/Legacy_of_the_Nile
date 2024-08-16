package com.Legacy.ofthe.Nile.ui.main.rules

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.Legacy.ofthe.Nile.R
import com.Legacy.ofthe.Nile.databinding.FragmentRulesBinding
import com.Legacy.ofthe.Nile.ui.main.chose_lvl.ChoseLvlFragment
import com.Legacy.ofthe.Nile.ui.main.start.StartGameFragment

class RulesFragment : Fragment() {

    private val binding by lazy { FragmentRulesBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBtnChoseLvlClickListener()
    }

    private fun setupBtnChoseLvlClickListener(){
        binding.btnChoseLvl.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.mainConteiner, StartGameFragment())
                addToBackStack(null)
                commit()
            }
        }
    }

}