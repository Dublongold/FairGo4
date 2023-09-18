package io.super_game.for_the_worthy.fairgo4.util

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.appcompat.widget.AppCompatImageButton
import androidx.lifecycle.ViewModel
import io.super_game.for_the_worthy.fairgo4.R
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FateAction: ViewModel() {
    private val privateTime = MutableStateFlow(0)
    private val privateScore = MutableStateFlow(0)
    private val privateGameWin = MutableStateFlow(false)
    private var timer = MutableStateFlow(true)

    private val privateCanMove = MutableStateFlow(false)

    var level = -1

    var pickedFirstId = -1
    lateinit var pickes: List<AppCompatImageButton>

    private val values = mutableListOf<Int>()

    val time: StateFlow<Int>
        get() = privateTime

    val score: StateFlow<Int>
        get() = privateScore

    val canMove: StateFlow<Boolean>
        get() = privateCanMove

    val gameWin: StateFlow<Boolean>
        get() = privateGameWin

    suspend fun startGame() {
        mix()
        timer.value = true
        privateGameWin.value = false
        privateCanMove.value = true
        privateScore.value = 0
        privateTime.value = 300 + 90 * ((level - 1) / 10).toInt()
        startTimer()
    }

    suspend fun mix() {
        privateCanMove.value = false
        for(i in pickes.indices) {
            if(values.size - 1 >= i) {
                values[i] = probablyValues.random()
            }
            else {
                values.add(probablyValues.random())
            }
        }
        for((index, pick) in pickes.withIndex()) {
            pick.setImageResource(values[index])
        }
        check(withAdd = false)
    }

    suspend fun pick(pickedId: Int): Boolean {
        privateCanMove.value = false
        var needDoCheck = false
        val forReturn = if(pickedFirstId != -1) {
            if (pickedId != pickedFirstId) {
                val compareId = listOf(1, -1, 5, -5)
                var temp = false
                for (c in compareId) {
                    if (pickedFirstId + c == pickedId) {
                        temp = true
                        break
                    }
                }
                if (temp) {
                    val t = values[pickedId]
                    values[pickedId] = values[pickedFirstId]
                    values[pickedFirstId] = t

                    pickes[pickedId].apply {
                        setImageResource(values[pickedId])
                        foreground = null
                    }
                    pickes[pickedFirstId].apply {
                        setImageResource(values[pickedFirstId])
                        foreground = null
                    }
                    pickedFirstId = -1
                    needDoCheck = true
                }
                else {
                    pickes[pickedFirstId].foreground = null
                    pickedFirstId = pickedId
                    privateCanMove.value = true
                    return true
                }
            }
            false
        }
        else {
            pickedFirstId = pickedId
            true
        }
        if(needDoCheck) {
            check()
        }
        else {
            privateCanMove.value = true
        }
        return forReturn
    }

    private suspend fun check(withAdd: Boolean = true) {
        var toScore = 0
        val mapped = mutableListOf<List<Int>>()
        do {
            val rows = List(pickes.size / 5) {
                values.subList(it*5, it*5 + 5).mapIndexed { index, value ->
                    value to (index + it * 5)
                }
            }
            val columns = List(5) { c ->
                List(pickes.size / 5) {
                    values[it * 5 + c] to it * 5 + c
                }
            }
            toScore = 0
            for(i in probablyValues) {
                for(e in columns) {
                    if(e.count { it.first == i } > 2) {
                        val append = when {
                            /*
                            1 1 1 0
                            0 1 1 1
                            0 0 1 1 1
                            0 0 0 1 1 1
                             */
                            e.size >= 3 && e[0].first == e[1].first && e[1].first == e[2].first -> 0
                            e.size >= 4 && e[1].first == e[2].first && e[2].first == e[3].first -> 1
                            e.size >= 5 && e[2].first == e[3].first && e[3].first == e[4].first -> 2
                            e.size >= 6 && e[3].first == e[4].first && e[4].first == e[5].first -> 3
                            else -> -1
                        }
                        if(append != -1) {
                            mapped.add(listOf(e[0 + append].second, e[1 + append].second, e[2 + append].second))
                            if(withAdd) {
                                toScore += 50
                            }
                        }
                    }
                }
                for(e in rows) {
                    if(e.count { it.first == i } > 2) {
                        val append = when {
                            e[0].first == e[1].first && e[1].first == e[2].first  -> 0
                            e[1].first == e[2].first && e[2].first == e[3].first -> 1
                            e[2].first == e[3].first && e[3].first == e[4].first -> 2
                            else -> -1
                        }
                        if(append != -1) {
                            mapped.add(listOf(e[0 + append].second, e[1 + append].second, e[2 + append].second))
                            toScore += 50
                        }
                    }
                }
            }
            if(toScore > 0) {
                if(withAdd) {
                    privateScore.value += toScore
                    delay(250)
                }
                for(map in mapped) {
                    for(m in map) {
                        pickes[m].setImageDrawable(ColorDrawable(0x00000000))
                    }
                }
                if(withAdd) {
                    delay(250)
                }
                for(map in mapped) {
                    for(m in map) {
                        values[m] = probablyValues.random()
                        pickes[m].setImageResource(values[m])
                    }
                }
            }
        }
        while (toScore > 0)
        if(score.value >= 100 + 100 * level) {
            Log.i("Check", "Game win: ${100 + 100 * level}, ${level}")
            gameWin()
        }
        privateCanMove.value = true
    }

    suspend fun startTimer() = coroutineScope {
        delay(1000)
        while(privateTime.value > 0 && timer.value && !privateGameWin.value) {
            privateTime.value -= 1
            delay(1000)
        }
    }

    suspend fun gameWin() {
        timer.value = false
        privateGameWin.value = true
    }

    companion object {
        val probablyValues = listOf(
            R.drawable.pick01,
            R.drawable.pick02,
            R.drawable.pick03,
            R.drawable.pick04,
            R.drawable.pick05,
            R.drawable.pick06,
            R.drawable.pick07,
            R.drawable.pick08,
            R.drawable.pick09,
            R.drawable.pick10
        )
    }
}