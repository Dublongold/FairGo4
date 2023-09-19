package com.koalagames.playfair.goapp.points

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.koalagames.playfair.goapp.R
import com.koalagames.playfair.goapp.util.advice
import com.koalagames.playfair.goapp.util.choice
import com.koalagames.playfair.goapp.util.importance
import com.koalagames.playfair.goapp.util.inf

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