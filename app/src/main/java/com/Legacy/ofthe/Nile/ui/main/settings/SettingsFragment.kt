package com.Legacy.ofthe.Nile.ui.main.settings

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.Legacy.ofthe.Nile.R
import com.Legacy.ofthe.Nile.data.PREFS_NAME
import com.Legacy.ofthe.Nile.data.SOUND_PREFS
import com.Legacy.ofthe.Nile.data.VOLUME_PREFS
import com.Legacy.ofthe.Nile.data.isMusicOn
import com.Legacy.ofthe.Nile.databinding.FragmentSettingsBinding
import com.Legacy.ofthe.Nile.ui.main.chose_lvl.ChoseLvlFragment
import com.Legacy.ofthe.Nile.ui.main.start.StartGameFragment

class SettingsFragment : Fragment() {

    private val binding by lazy { FragmentSettingsBinding.inflate(layoutInflater) }
    private val prefs by lazy { requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateBtnIcons()
        setupBtnClickListeners()
    }

    private fun updateBtnIcons(){
        val isVolumeOn = prefs.getBoolean(VOLUME_PREFS, true)
        val isSoundOn = prefs.getBoolean(SOUND_PREFS, true)
        val volumeImgId = if (isVolumeOn) R.drawable.sound_on else R.drawable.sound_off
        val soundImgId = if (isSoundOn) R.drawable.sound_on else R.drawable.sound_off
        binding.btnVolume.setImageResource(volumeImgId)
        binding.btnSound.setImageResource(soundImgId)
    }

    private fun setupBtnClickListeners(){
        binding.btnVolume.setOnClickListener {
            val isVolumeOn = prefs.getBoolean(VOLUME_PREFS, true)
            prefs.edit().putBoolean(VOLUME_PREFS, !isVolumeOn).apply()
            isMusicOn.isSoundOn.value = !isVolumeOn
            updateBtnIcons()
        }
        binding.btnSound.setOnClickListener {
            val isSoundOn = prefs.getBoolean(SOUND_PREFS, true)
            prefs.edit().putBoolean(SOUND_PREFS, !isSoundOn).apply()
            isMusicOn.isMusicOnLD.value = !isSoundOn
            updateBtnIcons()
        }
        binding.btnChoseLvl.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.mainConteiner, StartGameFragment())
                addToBackStack(null)
                commit()
            }
        }
    }

}