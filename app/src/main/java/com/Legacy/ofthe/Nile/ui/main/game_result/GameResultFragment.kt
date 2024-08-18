package com.Legacy.ofthe.Nile.ui.main.game_result

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.Legacy.ofthe.Nile.R
import com.Legacy.ofthe.Nile.data.PREFS_NAME
import com.Legacy.ofthe.Nile.data.VOLUME_PREFS
import com.Legacy.ofthe.Nile.databinding.FragmentGameResultBinding
import com.Legacy.ofthe.Nile.ui.main.game.easy.GameEasyFragment
import com.Legacy.ofthe.Nile.ui.main.game.hard.GameHardFragment
import com.Legacy.ofthe.Nile.ui.main.game.middle.GameMiddleFragment
import com.Legacy.ofthe.Nile.ui.main.start.StartGameFragment

private const val GAME_RESULT = "game_result"
private const val GAME_DURATION = "game_duration"
private const val GAME_LVL = "game_lvl"

class GameResultFragment : Fragment() {
    private var gameResult: Boolean = false
    private var gameDuration: Int? = null
    private var gameLvl:Int = 1
    private val binding by lazy { FragmentGameResultBinding.inflate(layoutInflater) }
    private var mMediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            gameResult = it.getBoolean(GAME_RESULT)
            gameDuration = it.getInt(GAME_DURATION)
            gameLvl = it.getInt(GAME_LVL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFragment()
    }

    private fun setupFragment(){
        playMusic()
        binding.btnToStart.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.mainConteiner, StartGameFragment())
                addToBackStack(null)
                commit()
            }
        }
        if (gameResult) setupWin()
        else setupLose()
    }

    private fun playSound() {
        val soundId = if (gameResult) R.raw.win_sound else R.raw.lose_sound
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(requireContext(), soundId)
            mMediaPlayer!!.isLooping = false
            mMediaPlayer!!.start()
        } else mMediaPlayer!!.start()
    }

    private fun playMusic(){
        val prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        if(prefs.getBoolean(VOLUME_PREFS, true)){
            playSound()
        }
    }

    private fun setupWin() {
        binding.tvResultHeader.text = requireContext().getString(R.string.congrats)
        val lvlAsText = when(gameLvl){
            1 -> requireContext().getString(R.string.easy)
            2 -> requireContext().getString(R.string.middle)
            else -> requireContext().getString(R.string.hard)
        }
        binding.tvResult.text = getString(R.string.level_complete, lvlAsText)
        binding.tvTimeValue.text = gameDuration?.let { convertIntToTime(it) }
        binding.btnNextLvl.text = getString(R.string.next_level)
        binding.btnNextLvl.setOnClickListener {
            val targetFragment = when(gameLvl){
                1 -> GameMiddleFragment()
                2 -> GameHardFragment()
                else -> GameHardFragment()
            }
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.mainConteiner, targetFragment)
                commit()
            }
        }
    }

    private fun convertIntToTime(timeInSeconds:Int):String{
        return String.format("%02d:%02d", timeInSeconds / 60, timeInSeconds % 60)
    }

    private fun setupLose() {
        binding.tvResultHeader.text = getString(R.string.lose)
        binding.tvResult.text = getString(R.string.wrong_sequence)
        binding.tvTime.visibility = View.GONE
        binding.tvTimeValue.text = getString(R.string.try_again)
        binding.btnNextLvl.text = getString(R.string.repeat)
        binding.btnNextLvl.setOnClickListener {
            val targetFragment = when(gameLvl){
                1 -> GameEasyFragment()
                2 -> GameMiddleFragment()
                else -> GameHardFragment()
            }
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.mainConteiner, targetFragment)
                commit()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(gameResult: Boolean, gameDuration: Int, gameLvl:Int) =
            GameResultFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(GAME_RESULT, gameResult)
                    putInt(GAME_DURATION, gameDuration)
                    putInt(GAME_LVL, gameLvl)
                }
            }
    }
}