package io.super_game.for_the_worthy.fairgo4.points

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.allViews
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import io.super_game.for_the_worthy.fairgo4.R
import io.super_game.for_the_worthy.fairgo4.util.FateAction
import io.super_game.for_the_worthy.fairgo4.util.advice
import io.super_game.for_the_worthy.fairgo4.util.getFateLevel
import io.super_game.for_the_worthy.fairgo4.util.inf
import io.super_game.for_the_worthy.fairgo4.util.leave
import io.super_game.for_the_worthy.fairgo4.util.observeChanges
import io.super_game.for_the_worthy.fairgo4.util.result
import kotlinx.coroutines.launch

class Fate: Fragment() {

    val action = FateAction()
    var level: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater inf R.layout.fate inf container
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mixAgain = getString(R.string.mix_again)
        view.apply {
            level = getFateLevel()
            action.level = level
            Log.i("Fate", "Level: $level")
            val mixButton = allViews.first {
                it is AppCompatButton && it.text == mixAgain
            }
            mixButton.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    action.mix()
                }
            }
            observeChanges(action.time) {
                findViewById<TextView>(R.id.timer).text =
                    getString(
                        R.string.timer,
                        getString(R.string.time, it / 60),
                        getString(R.string.time, it % 60)
                    )
                if(it == 0) {
                    findNavController().result(it, action.score.value)
                }
            }

            observeChanges(action.gameWin) {
                if(it) {
                    findNavController().result(action.time.value, action.score.value)
                }
            }

            findViewById<TextView>(R.id.level).text = getString(R.string.level, level.toString())
            observeChanges(action.score) {
                findViewById<TextView>(R.id.score).text = getString(R.string.score, it.toString())
            }
            findViewById<TextView>(R.id.need).text = getString(R.string.need, (100 + 100 * level).toString())
            findViewById<AppCompatImageButton>(R.id.home_button).setOnClickListener {
                findNavController().leave()
            }
            findViewById<AppCompatButton>(R.id.advice_button).setOnClickListener {
                findNavController().advice()
            }
            if(level > 10) {
                View.inflate(requireContext(), R.layout.pickes, findViewById(R.id.pickes))
            }
            if(level > 20) {
                View.inflate(requireContext(), R.layout.pickes, findViewById(R.id.pickes))
            }
            action.pickes = allViews.filter {
                it is AppCompatImageButton && it.parent is LinearLayoutCompat
            }.map { it as AppCompatImageButton }.toList().apply {
                forEachIndexed { index, it ->
                    it.setOnClickListener {
                        viewLifecycleOwner.lifecycleScope.launch {
                            if (action.pick(index)) {
                                it.foreground = ResourcesCompat.getDrawable(
                                    resources,
                                    R.drawable.picked_pick,
                                    null
                                )
                            }
                        }
                    }
                }
            }
            observeChanges(action.canMove) { canMove ->
                action.pickes.forEach {
                    it.isEnabled = canMove
                }
                mixButton.isEnabled = canMove
            }
            Log.i("Pickes", action.pickes.size.toString())
        }
        viewLifecycleOwner.lifecycleScope.launch {
            action.startGame()
        }
    }
}