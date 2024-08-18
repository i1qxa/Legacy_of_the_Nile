package com.Legacy.ofthe.Nile.data

import androidx.lifecycle.MutableLiveData
import com.Legacy.ofthe.Nile.R

const val PREFS_NAME = "nile_prefs"
const val VOLUME_PREFS = "volume_prefs"
const val SOUND_PREFS = "sound_prefs"
const val ANIM_DURATION:Long = 300
val listOfGameItems = listOf<Int>(
    R.drawable.item1,
    R.drawable.item2,
    R.drawable.item3,
    R.drawable.item4,
    R.drawable.item5,
    R.drawable.item6,
    R.drawable.item7,
    R.drawable.item8,
)

object isMusicOn{
    val isMusicOnLD = MutableLiveData<Boolean>(true)
    val isSoundOn = MutableLiveData<Boolean>(true)
}
