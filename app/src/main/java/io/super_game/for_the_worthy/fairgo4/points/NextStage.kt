package io.super_game.for_the_worthy.fairgo4.points

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import io.super_game.for_the_worthy.fairgo4.R
import io.super_game.for_the_worthy.fairgo4.util.FatesFileManager
import io.super_game.for_the_worthy.fairgo4.util.backChoose
import io.super_game.for_the_worthy.fairgo4.util.fate
import io.super_game.for_the_worthy.fairgo4.util.getResult
import io.super_game.for_the_worthy.fairgo4.util.inf
import io.super_game.for_the_worthy.fairgo4.util.leave
import kotlinx.coroutines.launch

class NextStage: DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater inf R.layout.next_stage inf container
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.apply {
            attributes?.also { attributes ->
                attributes.dimAmount = 0f
                this.attributes = attributes
            }
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        view.apply {
            val (time, score) = getResult()
            Log.i("Test", "Fragments: ${parentFragmentManager.fragments.size}")
            findViewById<TextView>(R.id.result).text = getString(if(time > 0) R.string.you_win else R.string.you_lose)
            findViewById<TextView>(R.id.timer).text = getString(R.string.timer, getString(R.string.time, time / 60), getString(R.string.time, time % 60))
            findViewById<TextView>(R.id.score).text = getString(R.string.score, score.toString())
            findViewById<AppCompatImageButton>(R.id.home).setOnClickListener {
                requireActivity().findNavController(R.id.points).backChoose()
            }
            val replay = findViewById<AppCompatImageButton>(R.id.replay)
            replay.setOnClickListener {
                requireActivity().findNavController(R.id.points).leave()
                viewLifecycleOwner.lifecycleScope.launch {
                    parentFragmentManager.fragments.firstOrNull {it is Fate}?.run {
                        this as Fate
                        action.startGame()
                    }
                }
            }
            val level = (parentFragmentManager.fragments.first() as? Fate)?.level ?: 0
            if(time > 0 && level < 30) {
                this as ViewGroup
                findViewById<ViewGroup>(R.id.actions).addView(layoutInflater.inflate(R.layout.next, LinearLayoutCompat(context)), 1)
                findViewById<AppCompatImageButton>(R.id.next).setOnClickListener {
                    requireActivity().findNavController(R.id.points).backChoose()
                    requireActivity().findNavController(R.id.points).fate(level + 1)
                }
                FatesFileManager(requireActivity().applicationContext.filesDir).also {
                    if(it.getCompletedFates() + 1 == level) {
                        it.setCompletedFates(level)
                    }
                }
            }
        }
    }
}