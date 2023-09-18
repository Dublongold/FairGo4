package io.super_game.for_the_worthy.fairgo4.points

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.super_game.for_the_worthy.fairgo4.R
import io.super_game.for_the_worthy.fairgo4.util.inf

class Decision: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater inf R.layout.decision inf container
    }
}