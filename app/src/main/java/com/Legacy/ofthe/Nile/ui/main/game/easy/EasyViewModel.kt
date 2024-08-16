package com.Legacy.ofthe.Nile.ui.main.game.easy

import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.Legacy.ofthe.Nile.data.GameItemWithCoordinate
import com.Legacy.ofthe.Nile.data.listOfGameItems

class EasyViewModel:ViewModel() {

    private var startGameState = mutableListOf<GameItemWithCoordinate>()
    private var currentGameState = mutableListOf<GameItemWithCoordinate>()
    private var mixTimes = 5

    fun setupGameItemImgResources(listViews:List<ImageView>){
        val listOfResources = listOfGameItems.shuffled()
        var index = 0
        listViews.map { imageView ->
            imageView.setImageResource(listOfResources[index])
            index++
            startGameState.add(GameItemWithCoordinate(imageView, imageView.x, imageView.y))
        }
        currentGameState = startGameState
    }

    fun mixGameItems(){
        if(mixTimes>=0){
            val shuffledItems = currentGameState.shuffled()
            val first = shuffledItems[0]
            val second = shuffledItems[1]
            val tmpGameState = mutableListOf(
                first.copy(x=second.x, y = second.y),
                second.copy(x=first.x, y = first.y),
                shuffledItems[2]
            )
            currentGameState = tmpGameState
            animateNewCoordinates()
        }else{
            TODO("НУЖНО СДЕЛАТЬ ЗАПУСК ИГРЫ")
        }

    }

    private fun animateNewCoordinates(){
        currentGameState.map {
            it.view.animate().apply {
                duration = 300
                x(it.x)
                y(it.y)
                withEndAction {
                    mixTimes--
                    mixGameItems()
                }
            }
        }
    }

}