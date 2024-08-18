package com.Legacy.ofthe.Nile.ui.main.game.easy

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.Legacy.ofthe.Nile.R
import com.Legacy.ofthe.Nile.data.MoveDirections
import com.Legacy.ofthe.Nile.data.PREFS_NAME
import com.Legacy.ofthe.Nile.data.VOLUME_PREFS
import com.Legacy.ofthe.Nile.databinding.FragmentGameEasyBinding
import com.Legacy.ofthe.Nile.ui.main.game_result.GameResultFragment
import com.Legacy.ofthe.Nile.ui.main.settings.SettingsFragment
import com.Legacy.ofthe.Nile.ui.main.start.StartGameFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameEasyFragment : Fragment(), OnTouchListener {

    private val binding by lazy { FragmentGameEasyBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<EasyViewModel>()
    private val listOfGameItemViews by lazy { listOf(binding.item1, binding.item2, binding.item3) }
    private var xStart = 0F
    private var xEnd = 0F
    private var yStart = 0F
    private var yEnd = 0F
    private var viewSwiped: ImageView? = null
    private var mMediaPlayer: MediaPlayer? = null

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
            viewModel.setupGameItemImgResources(listOfGameItemViews, 1)
            setupBtnClickListeners()
        }
        observeGameStart()
        observeGameResult()
        observePlayMoveSound()
    }

    private fun stopSound() {
        if (mMediaPlayer != null) {
            mMediaPlayer!!.stop()
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }


    private fun playSound() {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(requireContext(), R.raw.move_card_sound)
            mMediaPlayer!!.isLooping = false
            mMediaPlayer!!.start()
        } else mMediaPlayer!!.start()
    }

    private fun observePlayMoveSound(){
        val prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        if(prefs.getBoolean(VOLUME_PREFS, true)){
            viewModel.playMoveCardSoundLD.observe(viewLifecycleOwner){
                if (it){
                    stopSound()
                    playSound()
                }
            }
        }
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        v?.performClick()
        if (v in listOfGameItemViews) {
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    xStart = event.x
                    yStart = event.y
                    viewSwiped = v as ImageView
                }

                MotionEvent.ACTION_MOVE -> {
                    xEnd = event.x
                    yEnd = event.y
                }

                MotionEvent.ACTION_UP -> {
                    val xDiff = xEnd - xStart
                    val yDiff = yEnd - yStart
                    val xMod = if (xDiff < 0) xDiff * (-1) else xDiff
                    val yMod = if (yDiff < 0) yDiff * (-1) else yDiff
                    val moveDirection = if (xMod > yMod) {
                        if (xDiff < 0) MoveDirections.LEFT
                        else MoveDirections.RIGHT
                    } else {
                        if (yDiff < 0) MoveDirections.UP
                        else MoveDirections.DAWN
                    }
                    xStart = 0F
                    xEnd = 0F
                    yStart = 0F
                    yEnd = 0F
                    viewSwiped?.let { viewModel.moveCard(moveDirection, it) }
                    viewSwiped = null
                }
            }
        }
        return true
    }

    private fun observeGameStart(){
        viewModel.isGameStartedLD.observe(viewLifecycleOwner){
            binding.tvGameHint.text = getString(R.string.make_the_right_sequence)
            binding.btnMix.text = getString(R.string.done)
            setOnTouchListener()
        }
        viewModel.timerLD.observe(viewLifecycleOwner){
            binding.tvTimer.text = convertIntToTime(it)
        }
    }

    private fun observeGameResult(){
        viewModel.gameResultLD.observe(viewLifecycleOwner){
            val resultFragment = GameResultFragment.newInstance(it.gameResult, it.gameDuration, it.gameLvl)
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.mainConteiner, resultFragment)
                commit()
            }
        }
    }

    private fun convertIntToTime(timeInSeconds:Int):String{
        return String.format("%02d:%02d", timeInSeconds / 60, timeInSeconds % 60)
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

    private fun setOnTouchListener(){
        listOfGameItemViews.map {
            it.setOnTouchListener(this)
        }
    }

}