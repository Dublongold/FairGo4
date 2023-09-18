package io.super_game.for_the_worthy.fairgo4.points

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import io.super_game.for_the_worthy.fairgo4.R
import io.super_game.for_the_worthy.fairgo4.util.advice
import io.super_game.for_the_worthy.fairgo4.util.choice
import io.super_game.for_the_worthy.fairgo4.util.importance
import io.super_game.for_the_worthy.fairgo4.util.inf

class Familiarization: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater inf R.layout.familiarization inf container
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.apply {
            findViewById<AppCompatButton>(R.id.advice_button).setOnClickListener {
                findNavController().advice()
            }
            findViewById<AppCompatButton>(R.id.choice_button).setOnClickListener {
                findNavController().choice()
            }
            findViewById<AppCompatButton>(R.id.importance_button).setOnClickListener {
                findNavController().importance()
            }
        }
    }
}