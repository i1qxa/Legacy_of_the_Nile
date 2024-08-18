package com.Legacy.ofthe.Nile.ui.main.game.easy

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Legacy.ofthe.Nile.data.ANIM_DURATION
import com.Legacy.ofthe.Nile.data.GameInfo
import com.Legacy.ofthe.Nile.data.GameItemWithCoordinate
import com.Legacy.ofthe.Nile.data.MoveDirections
import com.Legacy.ofthe.Nile.data.listOfGameItems
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EasyViewModel : ViewModel() {

    private var gameLvl = 1
    private var startGameItemSequence = mutableListOf<GameItemWithCoordinate>()
    private var currentGameItemSequence = mutableListOf<GameItemWithCoordinate>()
    private var mixAmount = 5
    val timerLD = MutableLiveData<Int>()
    val isGameStartedLD = MutableLiveData<Any>()
    private var isGameStarted: Any? = null
    private var topLeftCorner = Pair(0F, 0F)
    private var bottomRightCorner = Pair(0F, 0F)
    val gameResultLD = MutableLiveData<GameInfo>()
    val playMoveCardSoundLD = MutableLiveData<Boolean>()

    fun setupGameItemImgResources(listViews: List<ImageView>, lvl: Int) {
        gameLvl = lvl
        val listOfResources = listOfGameItems.shuffled()
        var index = 0
        listViews.map { imageView ->
            imageView.setImageResource(listOfResources[index])
            index++
            startGameItemSequence.add(GameItemWithCoordinate(imageView, imageView.x, imageView.y))
        }
        val lastItemIndex = startGameItemSequence.size - 1
        topLeftCorner = Pair(startGameItemSequence[0].x, startGameItemSequence[0].y)
        bottomRightCorner =
            Pair(startGameItemSequence[lastItemIndex].x, startGameItemSequence[lastItemIndex].y)
        currentGameItemSequence = startGameItemSequence

    }

    fun mixGameItems() {
        if (isGameStarted == null) {
            animateMixGameItems()
        } else {
            checkGameResult()
        }
    }

    private fun checkGameResult() {
        var gameResult = true
        startGameItemSequence.map {
            if (!currentGameItemSequence.contains(it)) gameResult = false
        }
        val gameDuration = timerLD.value ?: 0
        gameResultLD.value = GameInfo(gameResult, gameDuration, gameLvl)
    }

    fun moveCard(moveDirection: MoveDirections, cardView: ImageView) {
        if (canMakeMove(moveDirection, cardView)) {
            val gameItem = currentGameItemSequence.find { it.view == cardView }
            if (gameItem != null) {
                val targetGameItem = findTargetGameItem(moveDirection, gameItem)
                currentGameItemSequence.remove(gameItem)
                currentGameItemSequence.add(
                    gameItem.copy(
                        x = targetGameItem.x,
                        y = targetGameItem.y
                    )
                )
                currentGameItemSequence.remove(targetGameItem)
                currentGameItemSequence.add(targetGameItem.copy(x = gameItem.x, y = gameItem.y))
                playMoveCardSoundLD.value = true
                currentGameItemSequence.map {
                    it.view.animate().apply {
                        duration = ANIM_DURATION
                        x(it.x)
                        y(it.y)
                    }
                }
            }
        }
    }

    private fun findTargetGameItem(
        moveDirection: MoveDirections,
        gameItem: GameItemWithCoordinate
    ): GameItemWithCoordinate {
        return when (moveDirection) {
            MoveDirections.UP -> {
                currentGameItemSequence.filter { it.x == gameItem.x && it.y < gameItem.y }
                    .sortedByDescending { it.y }[0]
            }

            MoveDirections.DAWN -> {
                currentGameItemSequence.filter { it.x == gameItem.x && it.y > gameItem.y }
                    .sortedBy { it.y }[0]
            }

            MoveDirections.LEFT -> {
                currentGameItemSequence.filter { it.y == gameItem.y && it.x < gameItem.x }
                    .sortedByDescending { it.x }[0]
            }

            MoveDirections.RIGHT -> {
                currentGameItemSequence.filter { it.y == gameItem.y && it.x > gameItem.x }
                    .sortedBy { it.x }[0]
            }
        }
    }

    private fun canMakeMove(moveDirection: MoveDirections, view: View): Boolean {
        when (moveDirection) {
            MoveDirections.UP -> {
                return if (view.y > topLeftCorner.second) true
                else false
            }

            MoveDirections.DAWN -> {
                return if (view.y < bottomRightCorner.second) true
                else false
            }

            MoveDirections.LEFT -> {
                return if (view.x > topLeftCorner.first) true
                else false
            }

            MoveDirections.RIGHT -> {
                return if (view.x < bottomRightCorner.first) true
                else false
            }
        }
    }

    private fun launchTimer() {
        viewModelScope.launch {
            while (true) {
                delay(1000)
                val currentTime = timerLD.value ?: 0
                timerLD.postValue(currentTime + 1)
            }
        }
    }

    private fun animateMixGameItems() {
        if (mixAmount >= 0) {
            val shuffledItems = currentGameItemSequence.shuffled().toMutableList()
            val first = shuffledItems[0]
            val second = shuffledItems[1]
            shuffledItems.remove(first)
            shuffledItems.remove(second)
            shuffledItems.add(first.copy(x = second.x, y = second.y))
            shuffledItems.add(second.copy(x = first.x, y = first.y))
            currentGameItemSequence = shuffledItems
            playMoveCardSoundLD.value = true
            currentGameItemSequence.map {
                it.view.animate().apply {
                    duration = ANIM_DURATION
                    x(it.x)
                    y(it.y)
                    withEndAction {
                        mixAmount--
                        animateMixGameItems()
                    }
                }
            }
        } else {
            if (isGameStarted == null) {
                launchTimer()
                isGameStartedLD.value = Unit
                isGameStarted = Unit
            }

        }

    }

}