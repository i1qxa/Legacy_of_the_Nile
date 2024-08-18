package com.Legacy.ofthe.Nile.ui.main

import android.content.Context
import android.content.pm.ActivityInfo
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.Legacy.ofthe.Nile.R
import com.Legacy.ofthe.Nile.data.PREFS_NAME
import com.Legacy.ofthe.Nile.data.SOUND_PREFS
import com.Legacy.ofthe.Nile.data.VOLUME_PREFS
import com.Legacy.ofthe.Nile.data.isMusicOn
import com.Legacy.ofthe.Nile.ui.exit.ExitFragment
import com.Legacy.ofthe.Nile.ui.main.start.StartGameFragment

class MainActivity : AppCompatActivity() {

    private var mMediaPlayer: MediaPlayer? = null
    private val prefs by lazy { getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        initSettings()
        observeSound()
    }

    private fun initSettings(){
        val isVolumeOn = prefs.getBoolean(VOLUME_PREFS, true)
        isMusicOn.isSoundOn.value = isVolumeOn
        val isSoundOn = prefs.getBoolean(SOUND_PREFS, true)
        isMusicOn.isMusicOnLD.value = isSoundOn
    }

    override fun onStop() {
        super.onStop()
        if(mMediaPlayer!=null){
            mMediaPlayer!!.release()
            mMediaPlayer=null
        }
    }

    private fun playSound() {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.main_music)
            mMediaPlayer!!.isLooping = true
            mMediaPlayer!!.start()
        } else mMediaPlayer!!.start()
    }

    private fun stopSound() {
        if (mMediaPlayer != null) {
            mMediaPlayer!!.stop()
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }

    private fun observeSound(){
        isMusicOn.isMusicOnLD.observe(this){
            if (it){
                playSound()
            }else{
                stopSound()
            }
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.findFragmentById(R.id.mainConteiner) is StartGameFragment) {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.mainConteiner, ExitFragment())
                addToBackStack(null)
                commit()
            }
        } else {
            super.onBackPressed()
        }
    }
}