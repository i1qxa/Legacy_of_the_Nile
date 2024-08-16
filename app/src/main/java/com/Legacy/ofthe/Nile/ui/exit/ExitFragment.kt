package com.Legacy.ofthe.Nile.ui.exit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.Legacy.ofthe.Nile.R
import com.Legacy.ofthe.Nile.databinding.FragmentExitBinding

class ExitFragment : Fragment() {

    private val binding by lazy { FragmentExitBinding.inflate(layoutInflater) }

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
        binding.btnYes.setOnClickListener {
            requireActivity().finishAffinity()
        }
        binding.btnNo.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

}