package io.super_game.for_the_worthy.fairgo4.points

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.allViews
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.super_game.for_the_worthy.fairgo4.R
import io.super_game.for_the_worthy.fairgo4.util.FatesFileManager
import io.super_game.for_the_worthy.fairgo4.util.fate
import io.super_game.for_the_worthy.fairgo4.util.inf
import io.super_game.for_the_worthy.fairgo4.util.leave

class Choice: Fragment() {

    private lateinit var fatesFileManager: FatesFileManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater inf R.layout.choice inf container
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fatesFileManager = FatesFileManager(requireActivity().applicationContext.filesDir)
        val completedFates = fatesFileManager.getCompletedFates()
        view.allViews.filter {
            it is AppCompatButton
        }.map {
            it as AppCompatButton
        }.forEachIndexed { index, it ->
            val buttonId = index + 1
            it.text = buttonId.toString()
            it.setOnClickListener {
                findNavController().fate(buttonId)
            }
            if(completedFates < index) {
                it.isEnabled = false
                it.setTextColor(Color.WHITE)
            }
        }
        view.findViewById<AppCompatImageButton>(R.id.home_button).setOnClickListener {
            findNavController().leave()
        }
    }
}